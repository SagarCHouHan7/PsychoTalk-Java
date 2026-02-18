//package com.psychotalk.repository;
//
//import com.psychotalk.model.Users;
//import com.psychotalk.model.account.User;
//import org.springframework.data.jpa.repository.JpaRepository;
//import org.springframework.data.jpa.repository.Query;
//import org.springframework.data.repository.query.Param;
//import org.springframework.stereotype.Repository;
//
//@Repository
//public interface UserRepoOld extends JpaRepository<Users, Integer> {
//
//    @Query("SELECT u FROM User u WHERE u.username = :username AND u.password = : password")
//    User findByUsernameAndPassword(@Param("username") String username , @Param("password") String password);
//
//
////    @Query("select * from users where email=: email")
////    Users findByEmail(String email);
//    @Query("SELECT u FROM Users u WHERE u.email = :email")
//    Users findByEmail(@Param("email") String email);
//
//    @Query("SELECT u FROM Users u WHERE u.username = :username")
//    Users findByUserName(@Param("username") String username);
//
//    @Query("SELECT u FROM Users u WHERE u.email=:email AND u.password=:password")
//    Users findByUserEmailAndUserPassword(@Param("email")String email, @Param("password")  String password);
//
//    @Query("SELECT u FROM Users u WHERE u.id=:id")
//    Users getUserById(@Param("id") Integer userId);
//
//
//}
