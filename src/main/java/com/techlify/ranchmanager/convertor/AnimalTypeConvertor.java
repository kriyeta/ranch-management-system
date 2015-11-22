package com.techlify.ranchmanager.convertor;

import java.util.HashMap;
import java.util.Map;

import javafx.util.StringConverter;

import com.techlify.ranchmanager.dao.AnimalType;

/**
 * @author kamal
 *
 */
public class AnimalTypeConvertor extends StringConverter<AnimalType> {

	private Map<Long, AnimalType> mapAnimalTypes = new HashMap<Long, AnimalType>();

	@Override
	public String toString(AnimalType animalType) {
		mapAnimalTypes.put(animalType.getId(), animalType);
		return animalType.getName() + " (" + animalType.getId() + ")";
	}

	@Override
	public AnimalType fromString(String animalType) {
		return mapAnimalTypes.get(animalType);
	}
}