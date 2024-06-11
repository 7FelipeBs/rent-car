//package com.rentcar.security.entity;
//
//import com.rentcar.security.enums.ERole;
//import jakarta.persistence.*;
//import lombok.Getter;
//import lombok.NoArgsConstructor;
//import lombok.Setter;
//
//@Entity
//@Getter
//@Setter
//@NoArgsConstructor
//@Table(name = "roles")
//public class Role {
//
//	  @Id
//	  @GeneratedValue(strategy = GenerationType.IDENTITY)
//	  @Column(name = "role_id")
//	  private long id;
//
//	  @Enumerated(EnumType.STRING)
//	  @Column(length = 20)
//	  private ERole name;
//
//	  public Role(ERole name) {
//		  this.name = name;
//	  }
//}
