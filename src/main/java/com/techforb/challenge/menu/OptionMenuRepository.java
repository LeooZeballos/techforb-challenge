package com.techforb.challenge.menu;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * Repository of the option menu.
 *
 * @author Leonel Zeballos
 */
public interface OptionMenuRepository extends JpaRepository<OptionMenu, Long> {
    /**
    * Gets a menu by its name.
    */
    Optional<OptionMenu> findByName(String name);
}
