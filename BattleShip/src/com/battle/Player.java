package com.battle;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.swing.JButton;

public class Player {

	// Map contains : type of the ship , position , how long it is
	private Map<String ,  List<String>> ships;
	private List<String> position;
	private List<JButton> listOfGridButtons;
	private int initialPos =-1;
	private List<String> hittedShips;
	private int up =1;
	private int down = 1;
	private char left=1;
	private char right = 1;
	
	public Player(List<JButton> listOfGridButtons) {
	
		this.position = new  ArrayList<String>();
		this.ships = new HashMap<String , List<String>>();
		this.listOfGridButtons = new ArrayList<>(listOfGridButtons);
		this.hittedShips = new ArrayList<>();
	}
	
	public int setShip(ActionEvent arg , int shipsettingType) {
		
		JButton button = (JButton)arg.getSource();
		
		String buttonNum = button.getText().substring(1);
		String buttonLetter = button.getText().substring(0,1);
		
		if(!isFieldAlreadySelected(button.getText())) {
		
		if(initialPos==-1) {
		initialPos++;
		position.add(button.getText());
		button.setBackground(Color.GREEN);
		
		--shipsettingType;
		
		System.out.println("first pos: " + button.getText());
		return shipsettingType;
		}
		
		// check for number down
		if(Integer.parseInt(position.get(initialPos).substring(1))+down== 
				Integer.parseInt(buttonNum) && left == 1 && right == 1 &&
				buttonLetter.equals(position.get(initialPos).substring(0, 1))) {
		
			position.add(button.getText());
			button.setBackground(Color.GREEN);
		
			down++;		
			System.out.println(down  + "button: " + buttonLetter + " buttonnum " + buttonNum);
			
			shipsettingType--;
		return shipsettingType;
		
		}
		// check for number up
		else if(Integer.parseInt(position.get(initialPos).substring(1))-up== 
				Integer.parseInt(buttonNum) && left == 1 && right == 1
				&& buttonLetter.equals(position.get(initialPos).substring(0, 1))) {
	
			position.add(button.getText());
			button.setBackground(Color.GREEN);
			up++ ;
			System.out.println(up  + "button: " + buttonLetter + " buttonnum " + buttonNum);
			shipsettingType--;
			return shipsettingType;
		}
		
		//check for left letter
		else if(position.get(initialPos).substring(0, 1).charAt(0)-left
			== buttonLetter.charAt(0) && up == 1 && down == 1 &&
			Integer.parseInt(buttonNum) == 
			Integer.parseInt(position.get(initialPos).substring(1))) {
			
			position.add(button.getText());
			button.setBackground(Color.GREEN);
			System.out.println(left  + "button: " + buttonLetter + " buttonnum " + buttonNum);
			
			left++;
			shipsettingType--;
			
		return shipsettingType;
		}
		
		//check for right letter
		else if(position.get(initialPos).substring(0, 1).charAt(0)+right
				== buttonLetter.charAt(0) && up == 1 && down == 1 &&
				Integer.parseInt(buttonNum) == 
				Integer.parseInt(position.get(initialPos).substring(1))) {
			
			System.out.println(right  + "button: " + buttonLetter + " buttonnum " + buttonNum);
			position.add(button.getText());
			button.setBackground(Color.GREEN);
			right++;
		shipsettingType--;
		
		return shipsettingType;
		
			}
		}
		
	 return shipsettingType;
	}
	
	public void addPositionToShip(String shipType) {
		ships.put(shipType, position);
		displayAllSetedShipsToConsole();
	
	}
	
	public void clearPosition() {
		position = new ArrayList<String>();
		initialPos =-1;
		up =1;
		down = 1;
		left=1;
		right = 1;
		 
	}
	
	public void clearAll() {
		position = new ArrayList<String>();
		ships.clear();
		hittedShips.clear();
		initialPos =-1;
		up =1;
		down = 1;
		left=1;
		right = 1;
		
	}
	
	private boolean  isFieldAlreadySelected(String buttonText) {
		
		for(List<String> r : ships.values()) {
			
			if(r.contains(buttonText)) {
				return true;
			}
			
			}
	
		System.out.println("field is empty");
	return false;
	}
	
	public void displayAllSetedShipsToConsole() {
		
		for(Entry<String, List<String>> entry:	ships.entrySet()) {
			String key = entry.getKey();
			System.out.print(key + " : ");
			Iterator<String> itr =entry.getValue().iterator();
			while(itr.hasNext()) {
				System.out.print( itr.next()+", ");
				}
			}
	}
	
	public boolean checkIfShipGetHit(String field) {
		
		for(List<String> s : ships.values()) {
			
			if(s.contains(field)) {
				s.remove(field);
				displayAllSetedShipsToConsole();
			
				return true;
			}
		}
		return false;
	}
	
	public boolean  checkIfAllShipsDrowned() {
	
	for(List<String> value : ships.values()) {
		
		if(!value.isEmpty()){
			return false;
		}
		
	}
	return true;
	}

	public List<String> addHittedOnTargetPosition(String targetHitPosition) {
		
		hittedShips.add(targetHitPosition);
		return hittedShips;
	}

	public List<String> getListOfHittedShips(){
		return hittedShips;
	}
	
	public List<JButton> getGrid() {
	
		return listOfGridButtons;
	}
	
	public  List<String> getAllPositionsOfShips() {
		List<String> allPositions = new ArrayList<>();
		
		for(List<String> s: ships.values()) {
			allPositions.addAll(s);
		}
		return allPositions;	
	}
	
}


