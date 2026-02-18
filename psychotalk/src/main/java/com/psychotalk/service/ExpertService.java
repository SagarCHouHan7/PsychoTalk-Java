package com.psychotalk.service;


import com.psychotalk.dto.PageResponseDto;
import com.psychotalk.dto.expertDto.ExpertVerificationRequestDto;
import com.psychotalk.dto.expertDto.ExpertProfileDto;
import com.psychotalk.dto.expertDto.VerificationResponseDto;
import com.psychotalk.model.account.Expert;
import com.psychotalk.repository.ExpertRepo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.UUID;

@Service
public class ExpertService {

      @Autowired
      private ExpertRepo expertRepo;

    public VerificationResponseDto getVerified(MultipartFile file, ExpertVerificationRequestDto dto) throws IOException {
        if(getCurrentExpert().isVerificationSubmitted()){
            VerificationResponseDto response = new VerificationResponseDto();
            response.setMessage("Please do not rerequest! Your request is under review. Admin will contact you soon.");
            response.setVerificationSubmitted(true);
            return response;
        }
        if(file.getSize() > 5*1024*1024) throw  new IllegalArgumentException("File is too big");
        String contentType = file.getContentType();
        String originalFileName = file.getOriginalFilename();

        if ((contentType == null ||
                !contentType.equalsIgnoreCase("application/pdf")) &&
                (originalFileName == null ||
                        !originalFileName.toLowerCase().endsWith(".pdf"))) {

            throw new RuntimeException("Only PDF files are allowed");
        }
        String uploadDir = "assets/uploads/documents";
        Path uploadPath = Path.of(uploadDir).toAbsolutePath().normalize();
        Files.createDirectories(uploadPath);
        String fileName = UUID.randomUUID()+"_"+file.getOriginalFilename();
        Path filePath = uploadPath.resolve(fileName);
        file.transferTo(filePath.toFile());

        Expert expert = mapDtoVerificationToExpert(dto);
        expert.setDocumentPath(fileName);
        expert.setVerificationSubmitted(true);
        expertRepo.save(expert);

        VerificationResponseDto response = new VerificationResponseDto();
        response.setMessage("Your request is under review. Admin will contact you soon.");
        response.setVerificationSubmitted(true);
        return response;
    }

    public ExpertProfileDto getMyProfile(){
        Expert expert = getCurrentExpert();
        ExpertProfileDto dto = new ExpertProfileDto();
        BeanUtils.copyProperties(expert , dto);
        dto.setQualifications(expert.getQualification());
        dto.setRole("EXPERT");
        return dto;
    }


    public PageResponseDto<ExpertProfileDto> getAllExperts(int page , int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("rating").descending());
        Page<Expert> expertPage = expertRepo.findAllVerifiedExpert(pageable);
        List<ExpertProfileDto> content = expertPage
                .getContent()
                .stream()
                .map(this::mapToExpertProfileDto)
                .toList();
        return mapToPageResponse(expertPage , content);
    }

    public ExpertProfileDto getExpertById(Long id) {
        Expert expert = expertRepo.findById(id).orElseThrow(()->new RuntimeException("Expert not found"));
        if(!expert.isVerified()) throw new RuntimeException("Expert not verified");
        return mapToExpertProfileDto(expert);
    }

    private ExpertProfileDto mapToExpertProfileDto(Expert expert){
        ExpertProfileDto dto = new ExpertProfileDto();
        BeanUtils.copyProperties(expert , dto);
        dto.setQualifications(expert.getQualification());
        dto.setRole("EXPERT");
        return dto;
    }

    private PageResponseDto<ExpertProfileDto> mapToPageResponse(Page<Expert> page , List<ExpertProfileDto> content){
        PageResponseDto<ExpertProfileDto> dto = new PageResponseDto<>();
        dto.setContent(content);
        dto.setSize(page.getSize());
        dto.setPage(page.getNumber());
        dto.setLast(page.isLast());
        dto.setTotalPages(page.getTotalPages());
        dto.setTotalElements(page.getTotalElements());
        return dto;
    }

    private Expert mapDtoVerificationToExpert(ExpertVerificationRequestDto dto){
        Expert expert = getCurrentExpert();
        expert.setDob(dto.getDob());
        expert.setEmail(dto.getEmail());
        expert.setAddress(dto.getAddress());
        expert.setAbout(dto.getAbout());
        expert.setFees(dto.getFees());
        expert.setExperience(dto.getExperience());
        expert.setGender(dto.getGender());
        expert.setPhoneNo(dto.getPhoneNo());
        expert.setFullName(dto.getFullName());
        expert.setQualification(dto.getQualifications());
        return expert;
    }

    private Expert getCurrentExpert(){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();
        return expertRepo.findByUsername(username).orElseThrow(()-> new RuntimeException("username not found"));
    }


//    public String expertRegistration(Experts expert) {
//        Experts dbExpert = expertRepoOld.findExpertByEmailAndPassword(expert.getEmail() , expert.getPassword());
//        JSONObject json = new JSONObject();
//        if(dbExpert==null){
//            Date date = getDate();
//            expert.setJoiningDate(date);
//            expertRepoOld.save(expert);
//            return  "{\"status\":\"success\"}";
//        }
//        else{
//            System.out.println(expert.getQualification());
//            return "{\"status\":\"failure\":\"Already Registered\"}";
//        }
//    }
//
//    private Date getDate(){
//        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//        String formattedDate = formatter.format(new Date());
//        try {
//            return formatter.parse(formattedDate);
//        } catch (ParseException e) {
//            throw new RuntimeException(e);
//        }
//    }
//
//    public JSONObject getExpertByEmailAndPassword(Experts expert) {
//        Experts dbExpert = expertRepoOld.findExpertByEmailAndPassword(expert.getEmail() , expert.getPassword());
//        JSONObject json = new JSONObject();
//        if(dbExpert != null){
//            String token = JwtUtil.generateToken(dbExpert.getEmail());
//            json.put("token" , token);
//            Experts loggedInExpert = expertRepoOld.getExpertByExpertId(dbExpert.getId());
//            json.put("expert" , loggedInExpert);
//            json.put("status" , "success");
//        }
//        else{
//
//            json.put("status" , "failure");
//            json.put("errorMsg" , "Incorrect UserName or Password");
//        }
//
//        return json;
//
//    }
//
//    public List<Experts> getAllExpertsProfile() {
//        return expertRepoOld.getAllExpertsProfile();
//    }
//
//    public Experts getExpertByExpertId(Integer expertId) {
//        return expertRepoOld.getExpertByExpertId(expertId);
//    }
}
