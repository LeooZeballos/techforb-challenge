package com.techforb.challenge.menu;

import jakarta.persistence.*;
import lombok.Data;
import java.util.List;

/**
 * Represents an option menu with a list of options to choose from.
 *
 * @author Leonel Zeballos
 */
@Entity
@Table(name = "option_menu")
@Data
public class OptionMenu {

    /**
     * The id of the option menu.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    /**
     * The name of the option menu.
     */
    @Column(name = "name")
    private String name;

    /**
     * The order of the option menu.
     */
    @Column(name = "enabled")
    private Boolean enabled;

    /**
     * The options of the option menu.
     */
    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "option_menu_id")
    private List<Option> options;

}
