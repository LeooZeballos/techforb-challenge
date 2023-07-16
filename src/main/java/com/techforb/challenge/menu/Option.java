package com.techforb.challenge.menu;

import com.techforb.challenge.user.Role;
import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import java.util.List;

/**
 * Represents an option of a menu.
 * It has a list of roles that can use it.
 *
 * @author Leonel Zeballos
 */
@Entity
@Table(name = "option")
@Data
public class Option {

    /**
     * The id of the option.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    /**
     * The name of the option.
     */
    @Column(name = "name")
    @Length(min = 3, max = 50)
    private String name;

    /**
     * The icon of the option.
     */
    @Column(name = "icon")
    @Length(min = 3, max = 50)
    private String icon;

    /**
     * The url of the option.
     */
    @Column(name = "url")
    @Length(max = 2048) // max length of a URL
    private String url;

    /**
     * The order of the option.
     */
    @Column(name = "_order")
    @Min(0)
    @Max(100)
    private int order;

    /**
     * The roles that can use the option.
     */
    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "option_role")
    @Enumerated(EnumType.STRING)
    private List<Role> roles;

}
