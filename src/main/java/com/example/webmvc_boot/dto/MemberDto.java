package com.example.webmvc_boot.dto;

import lombok.*;

import javax.validation.constraints.NotBlank;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MemberDto {

    public interface group1 {};
    public interface group2 {};

    @NotBlank(groups = {group1.class}, message = "아이디는 비어있으면 안됩니다.")
    private String myId;
    
    @NotBlank(groups = {group2.class}, message = "비밀번호는 비어있으면 안됩니다.")
    private String myPwd;
}