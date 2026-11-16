package com.szymong.user_service.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Setter
@Getter
@Entity
@NoArgsConstructor
@Table(name = "users")
public class User {

  @Id
  @GeneratedValue(strategy =  GenerationType.IDENTITY)
  @Column(name = "id")
  private Long id;

  @Column(name="clerk_id", nullable = false, unique = true)
  private String clerkId;

  @Column(name = "email", nullable = false, unique = true)
  private String email;

  @Column(name = "username", nullable = false, unique = true)
  private String username;

  @Column(name = "profile_image_public_id")
  private String profileImagePublicId;

  @Column(name = "deleted_at")
  private LocalDateTime deletedAt;

  @Column(name = "is_deleted", nullable = false)
  private Boolean isDeleted = false;

  @CreationTimestamp
  @Column(name = "created_at", nullable = false)
  private LocalDateTime createdAt;

  public User(String clerkId, LocalDateTime createdAt, String email, String profileImagePublicId, String username) {
    this.clerkId = clerkId;
    this.createdAt = createdAt;
    this.email = email;
    this.profileImagePublicId = profileImagePublicId;
    this.username = username;
  }

}
