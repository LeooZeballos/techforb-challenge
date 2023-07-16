package com.techforb.challenge.menu;

/**
 * The service of the option menu.
 *
 * @see OptionMenu
 * @author Leonel Zeballos
 */
public interface IOptionMenuService {

    /**
     * Gets a menu by its name.
     */
    OptionMenu getOptionMenuByName(String name);

}
