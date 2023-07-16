package com.techforb.challenge.menu;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * The implementation of the service of the option menu.
 *
 * @see IOptionMenuService
 * @author Leonel Zeballos
 */
@Service
@RequiredArgsConstructor
public class OptionMenuServiceImpl implements IOptionMenuService {

    /**
     * The repository of the option menu.
     */
    private final OptionMenuRepository optionMenuRepository;

    /**
     * Gets a menu by its name.
     */
    @Override
    public OptionMenu getOptionMenuByName(String name) {
        return optionMenuRepository.findByName(name)
                .orElseThrow(() -> new IllegalArgumentException("Option menu not found with name: " + name));
    }

}
