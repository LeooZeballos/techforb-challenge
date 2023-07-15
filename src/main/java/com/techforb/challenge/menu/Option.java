package com.techforb.challenge.menu;

import com.techforb.challenge.user.Role;
import jakarta.persistence.*;
import lombok.Data;

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
    private String name;

    /**
     * The icon of the option.
     */
    @Column(name = "icon")
    private String icon;

    /**
     * The url of the option.
     */
    @Column(name = "url")
    private String url;

    /**
     * The order of the option.
     */
    @Column(name = "_order")
    private int order;

    /**
     * The roles that can use the option.
     */
    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "option_role")
    @Enumerated(EnumType.STRING)
    private List<Role> roles;

}
