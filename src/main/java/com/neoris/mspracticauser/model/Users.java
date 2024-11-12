package com.neoris.mspracticauser.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Set;


@EqualsAndHashCode(callSuper=false)
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name="users")
public class Users {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "email")
    @NotNull
    @NotBlank
    String email;

    @Column(name = "name")
    @NotNull @NotBlank
    String name;

    @Column(name = "password")
    @NotNull
    @NotBlank
    String password;

    @Column(name = "status")
    @NotNull @NotBlank
    String status;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "user_roles",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id")
    )
    private Set<Role> roles;


}
