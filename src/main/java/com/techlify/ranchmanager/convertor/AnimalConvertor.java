package com.techlify.ranchmanager.convertor;

import java.util.HashMap;
import java.util.Map;

import javafx.util.StringConverter;

import com.techlify.ranchmanager.dao.Animal;

/**
 * @author kamal
 *
 */
public class AnimalConvertor extends StringConverter<Animal>{
	private Map<Long, Animal> mapAnimal = new HashMap<Long, Animal>();

	@Override
	public String toString(Animal animal) {
		mapAnimal.put(animal.getId(), animal);
		return "animal #"+animal.getNumbers() + " ( id: " + animal.getId() + ")";
	}

	@Override
	public Animal fromString(String animal) {
		return mapAnimal.get(animal);
	}
}
