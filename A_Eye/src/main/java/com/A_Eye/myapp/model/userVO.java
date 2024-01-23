package com.A_Eye.myapp.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class userVO {
   
   private String name;
   private String password;
   private String email;
   private String ssn;
}