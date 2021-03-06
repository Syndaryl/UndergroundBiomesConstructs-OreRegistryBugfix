package com.aang23.undergroundbiomes.api.enums;

/**
 * @author LouisDB
 */
public enum UBStoneStyle {
    STONE, GRAVEL, COBBLE, STONE_STAIRS, COBBLE_STAIRS, BRICK, SAND, INFESTED_STONE, STONE_BUTTON, COBBLE_WALL;

    @Override
    public String toString() {
        return name().toLowerCase();
    }
}