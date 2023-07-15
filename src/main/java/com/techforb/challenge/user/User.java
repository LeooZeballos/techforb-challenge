package com.techforb.challenge.user;

import com.techforb.challenge.account.Account;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

/**
 * The user entity.
 * It implements {@link UserDetails} to be used by Spring Security.
 *
 * @see UserDetails
 * @author Leonel Zeballos
 */
@Entity
@Table(
        name = "_user", // user is a reserved word in Postgres
        uniqueConstraints = {@UniqueConstraint(columnNames = {"dni", "document_type"})}
)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class User implements UserDetails {

    /**
     * The user id. It is generated automatically by the database.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    /**
     * The user dni. It is unique. It is used as a username.
     */
    @Column(nullable = false)
    private String dni;

    /**
     * The user password. It is encoded.
     */
    @Column(nullable = false)
    private String password;

    /**
     * The user email. It is unique.
     */
    @Column(nullable = false, unique = true)
    private String email;

    /**
     * The user first name.
     */
    @Column(nullable = false)
    private String firstName;

    /**
     * The user last name.
     */
    @Column(nullable = false)
    private String lastName;

    /**
     * The user address.
     */
    @Column(nullable = false)
    @Builder.Default
    private boolean enabled = true;

    /**
     * The account non expired flag. It is used by Spring Security.
     */
    @Column(nullable = false)
    @Builder.Default
    private boolean accountNonExpired = true;

    /**
     * The account non-locked flag. It is used by Spring Security.
     */
    @Column(nullable = false)
    @Builder.Default
    private boolean accountNonLocked = true;

    /**
     * The credentials non-expired flag. It is used by Spring Security.
     */
    @Column(nullable = false)
    @Builder.Default
    private boolean credentialsNonExpired = true;

    /**
     * The user creation date. By default, it is the current date.
     */
    @Column(updatable = false, nullable = false)
    @Builder.Default
    private LocalDateTime createdAt = LocalDateTime.now();

    /**
     * The dni type. It has to be unique with the dni.
     */
    @Column(nullable = false, name = "document_type")
    private DocumentType documentType;

    /**
     * The user accounts.
     */
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Account> accounts;

    /**
     * The user roles.
     */
    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "user_role")
    @Enumerated(EnumType.STRING)
    private List<Role> roles;


    /**
     * Returns the user authorities.
     * It is used by Spring Security.
     * Map the user roles to {@link GrantedAuthority}.
     *
     * @return The user authorities.
     */
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return roles.stream()
                .map(role -> (GrantedAuthority) role::name)
                .collect(Collectors.toList());
    }

    /**
     * Returns the user username. It is used by Spring Security.
     *
     * @return The user username. It is the document type + : + dni.
     */
    @Override
    public String getUsername() {
        return documentType + ":" + dni;
    }

}
