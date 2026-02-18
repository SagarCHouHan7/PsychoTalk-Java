package com.psychotalk.service.mediaService;

import com.psychotalk.dto.PageResponseDto;
import com.psychotalk.dto.mediaDto.ImageResponseDto;
import com.psychotalk.model.account.Account;
import com.psychotalk.model.account.Expert;
import com.psychotalk.model.mediaModels.ImageEntity;
import com.psychotalk.repository.AccountRepo;
import com.psychotalk.repository.ImageEntityRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
public class MediaService {

    @Autowired
    private AccountRepo accountRepo;
    @Autowired
    private ImageEntityRepo imageEntityRepo;

    @Value("${file.upload-dir}")
    private String uploadDir;

    public ImageResponseDto saveImage(MultipartFile file, String caption) throws IOException {
        if (file.isEmpty()) {
            throw new RuntimeException("File is empty");
        }

        String contentType = file.getContentType();
        if(contentType == null || !contentType.startsWith("image/"))
            throw new IllegalArgumentException("only images are excepted");

        BufferedImage image1 = ImageIO.read(file.getInputStream());
        if(image1 == null) throw  new IllegalArgumentException("file is not an image");

        Path uploadPath = Paths.get(uploadDir).toAbsolutePath().normalize();
        Files.createDirectories(uploadPath);
        String fileName = UUID.randomUUID() + "_" + file.getOriginalFilename();
        Path filePath = uploadPath.resolve(fileName);
        file.transferTo(filePath.toFile());

        ImageEntity image = new ImageEntity();
        image.setCaption(caption.trim());
        image.setCommentCount(0);
        image.setLikeCount(0);
        image.setUploadedAt(LocalDateTime.now());
        image.setFileName(fileName);
        image.setFilePath("/images/"+fileName);
        image.setAccount(getCurrentAccount());
        return mapToImageEntityDto(imageEntityRepo.save(image));
    }

    public PageResponseDto<ImageResponseDto> geAllMedia(int page, int size) {
        Pageable pageable = PageRequest.of(page , size , Sort.by("uploadedAt").descending());
        Page<ImageEntity> imageEntityPage = imageEntityRepo.findAll(pageable);
        List<ImageResponseDto> content = imageEntityPage.getContent().stream()
                .map(this::mapToImageEntityDto)
                .toList();
        return mapToPageResponseDto(content , imageEntityPage);
    }

    private PageResponseDto<ImageResponseDto> mapToPageResponseDto(List<ImageResponseDto> content, Page<ImageEntity> imageEntityPage) {
        PageResponseDto<ImageResponseDto> page = new PageResponseDto<>();
        page.setTotalElements(imageEntityPage.getTotalElements());
        page.setTotalPages(imageEntityPage.getTotalPages());
        page.setPage(imageEntityPage.getNumber());
        page.setSize(imageEntityPage.getSize());
        page.setLast(imageEntityPage.isLast());
        page.setContent(content);
        return page;
    }

    private ImageResponseDto mapToImageEntityDto(ImageEntity save) {
        ImageResponseDto dto = new ImageResponseDto();
        dto.setId(save.getId());
        dto.setCaption(save.getCaption());
        dto.setAccountId(save.getAccount().getId());
        dto.setUsername(save.getAccount().getUsername());
        dto.setFileName(save.getFileName());
        dto.setFilePath(save.getFilePath());
        dto.setCommentCount(save.getCommentCount());
        dto.setLikeCount(save.getLikeCount());
        dto.setUploadedAt(save.getUploadedAt());
        Account account = save.getAccount();
        if(account instanceof Expert) dto.setRole("EXPERT");
        return dto;
    }

    private Account getCurrentAccount(){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();
        Account account =  accountRepo.findByUsername(username);
        if(account == null) throw new RuntimeException("User not found");
        return account;
    }


}
