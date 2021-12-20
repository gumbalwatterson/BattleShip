package com.battle;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.UIManager;
import javax.swing.border.MatteBorder;

public class WarShips extends JFrame implements ActionListener{

	private JPanel contentPane;
	private JPanel contentPane2;
	private JPanel contentPane3;
	private JPanel contentPane4;
	private JButton startButton;
	private JButton setPlayer1;
	private JButton setPlayer2;
	private String shipType = "";
	private boolean player1Checked;
	private boolean player2Checked;
	private Player player1;
	private Player player2;
	private int shipsettingType;
	private JComboBox<String> jcbox ;
	private JLabel shipSelectlabel;
	private List<JButton> listOfGridButtons = new ArrayList<>();
	private JTextArea textarea;
	private JButton shipRestButton;
	private Color initialcolor;
	private JButton checkYourPositionOnGid;
	private boolean player1Set;
	private boolean player2Set;
	private boolean start;
	private Random random;
	private boolean hit;
	private int checkposition =1;
	
	//ships:
	// Carrier 5 fields , Battleship 4 fields , Destroyer 3 fields , Submarine 3 fields 
	// Patrol Boat 2 fields
	
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					WarShips frame = new WarShips();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public WarShips() {
		
		setTitle("BattleShips");
		Color color = UIManager.getColor ( "Panel.background" );
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 950, 600);
		getContentPane().setLayout(new BorderLayout(3,1));
		getRootPane().setBorder(new MatteBorder(1, 1, 30, 30, color));
		contentPane = new JPanel();
		contentPane.setBorder(new MatteBorder(3, 3, 30, 30, color));
		contentPane.setLayout(new GridLayout(10, 10, 0, 0));
		
		contentPane2 = new JPanel();
		contentPane2.setBorder(new MatteBorder(30, 30, 30, 30, color));
		contentPane2.setLayout(new GridLayout(10, 1, 5, 5));
		
		contentPane3 = new JPanel();
		contentPane3.setBorder(new MatteBorder(30, 80, 1, 178, color));
		contentPane3.setLayout(new GridLayout(1, 10, 1, 1));
		
		contentPane4 = new JPanel();
		contentPane4.setBorder(new MatteBorder(4, 30, 35, 1, color));
		contentPane4.setLayout(new GridLayout(10, 1, 3, 0));
		
		
		char charLetters = 65;
		
		// adding labels letters
		for(int i =0 ; i<10; i++) {
			JLabel toplabel  = new JLabel(""+charLetters);
			toplabel.setSize(10, 10);
			contentPane3.add(toplabel);
			charLetters++;
		}
		
		
		
		// adding labels numbers
		for(int i =0 ; i<10; i++) {
			JLabel leftLabel  = new JLabel(""+(i+1));
			leftLabel.setSize(10, 10);
			contentPane4.add(leftLabel);
				
		}
		
		getContentPane().add(contentPane, BorderLayout.CENTER);
		getContentPane().add(contentPane2, BorderLayout.EAST);
		getContentPane().add(contentPane3, BorderLayout.NORTH);
		getContentPane().add(contentPane4, BorderLayout.WEST);
		
		setGrid();
		
		startButton = new JButton("Start");
		startButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
			
				checkYourPositionOnGid.setEnabled(true);
				start = true;
				
				if(random.nextBoolean()) {
					player1Checked = true;
					player2Checked = false;
					shipSelectlabel.setText("Player 1 turn");
				
				}else {
					player1Checked = false;
					player2Checked = true;
					shipSelectlabel.setText("Player 2 turn");
					
				}
				startButton.setEnabled(false);
			}
		});
		
		startButton.setEnabled(false);
		contentPane2.add(startButton);
		
		// player 1 set his ships
		setPlayer1 = new JButton("Set Player1");
		setPlayer1.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				if(!player2Checked) {
				player1Checked = true;
				setPlayer1.setBackground(Color.GREEN);
				shipSelectlabel.setText("Player 1 is check");
				
				}
			}
		});
		contentPane2.add(setPlayer1);
		
		setPlayer2 = new JButton("Set Player2");
		setPlayer2.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				if(!player1Checked) {
					player2Checked = true;
					setPlayer2.setBackground(Color.GREEN);
					shipSelectlabel.setText("Player 2 is check");
				
					}

				
			}
		});
		contentPane2.add(setPlayer2);
		
		// label for indicating which ship is currently setting into the grid
		shipSelectlabel = new JLabel();
		contentPane2.add(shipSelectlabel);
		
		jcbox =  new JComboBox<String>();
		
		initJComboBoxWithShipIteams();
		
		jcbox.addItemListener(new ItemListener() {
			
			@Override
			public void itemStateChanged(ItemEvent e) {
				
				if ( player1Checked || player2Checked && e.getStateChange() == ItemEvent.SELECTED ) {
			          Object item = e.getItem();
			          shipType = item.toString();
			      
			         
			        	  textarea.setText("current ship set:\n" + shipType);
			          
				}
			}
		});
		
		//jcbox.setSelectedIndex(-1);
		contentPane2.add(jcbox);
		
		textarea= new JTextArea();
		textarea.setSize(10, 30);
		textarea.setEditable(false);
		contentPane2.add(textarea);
		
		shipRestButton = new JButton("Reset");
		shipRestButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				
				clearAllSet();
			}
		});
		
		contentPane2.add(shipRestButton);
		
		checkYourPositionOnGid = new JButton("Check position");
		checkYourPositionOnGid.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
			
				if(checkposition>0) {
				resetMainGird();
				displayPlayerShipsPositions();
				checkposition=0;
				}
				else{
				resetMainGird();
				setPlayerGid();
				checkposition=1;
				}
			}
		});
		checkYourPositionOnGid.setEnabled(false);
		contentPane2.add(checkYourPositionOnGid);
		
		// Carrier 5 fields , Battleship 4 fields , Destroyer 3 fields , Submarine 3 fields 
		// Patrol Boat 2 fields
	
		// last operations of Constructor creating Players objects
		player1 = new Player(listOfGridButtons);
		player2 = new Player(listOfGridButtons);
		random = new Random();
	}

	private void setGrid() {
		
		char charval = 65;
		
		int num =0;
		for(int i =0 ; i<100 ; i++) {
		
			
			if(i%10==0) {
				num++;
			}
			JButton j = new JButton();  
			j.addActionListener(this);
			j.setSize(10, 10);
		
			if(i==1) {
			 initialcolor =	j.getBackground();
			}
			
			if(i>1 && i % 10 == 0) {
				charval=65;
				j.setText(""+charval+""+num);
				listOfGridButtons.add(j);
				contentPane.add(j);
				charval++;
				continue;	
			}
			j.setText(""+charval+""+num);
			listOfGridButtons.add(j);
			contentPane.add(j);
			charval++;
		}
	
		
	}

	private void initJComboBoxWithShipIteams() {
		jcbox.addItem("Carrier");
		jcbox.addItem("Battleship");
		jcbox.addItem("Destroyer");
		jcbox.addItem("Submarine");
		jcbox.addItem("Patrol Boat");
		jcbox.setSelectedIndex(-1);
	}
	
	private void removeAllIteamsInJComboBox(){
		jcbox.removeAllItems();	
	}
	
	@Override
	public void actionPerformed(ActionEvent arg) {
		System.out.println("inside");
		
		if(start) {
			playerTurn(arg);
		}
		else if(player1Checked) {
			selectingShip(arg);
		}
		else if(player2Checked) {
			selectingShip(arg);
		}
		else {
			shipSelectlabel.setText("set player");
		
		}
		
	}

	private void playerTurn(ActionEvent arg) {
	
	JButton button = (JButton) arg.getSource();
	System.out.println(button.getText());
	
	int select = player1Checked ? 1 : 0;
	
	switch(select) {
	
	case 1 : hit = player2.checkIfShipGetHit(button.getText());
			 break;
	case 0 : hit = player1.checkIfShipGetHit(button.getText());
			 break;
	}
	
	if(hit) {
		System.out.println("You have hited ship:" + button.getText());
		setHittedOnTarget(button);
	
	}else {
		System.out.println("You have missed");
		}
	
	checkIfPlayerWin();
	
	nextTurn(select);
	
	}
	
	private void checkIfPlayerWin() {
	
		if(player1Checked && player2.checkIfAllShipsDrowned() && start) {
			
			victory("player1 won");
			
		} 
		if(player2Checked && player1.checkIfAllShipsDrowned() && start)
		{
			victory("player2 won");
			
		}
		
	}

	private void victory(String string) {
		
		checkYourPositionOnGid.setEnabled(false);
		shipRestButton.setEnabled(true);
		clearAllSet();
		JOptionPane.showMessageDialog(null,string,"Victory",1);	
	}

	private void nextTurn(int select) {
	
		
		switch(select) {
		
		case 1: player1Checked = false;
				player2Checked = true;
				shipSelectlabel.setText("Player 2 turn");
				break;
				
		case 0: player1Checked = true;
				player2Checked = false;
				shipSelectlabel.setText("Player 1 turn");
				break;
		}
		
			resetMainGird();
			setPlayerGid();
		
	}

	private void setPlayerGid() {
		
		if(player1Checked) {
			setCurrentPlayerHitsIntoMainGrid(player1.getListOfHittedShips());
		}
		else {
			setCurrentPlayerHitsIntoMainGrid(player2.getListOfHittedShips());
		}
		
	}

	private void resetMainGird() {
		
		for(JButton b : listOfGridButtons) {
			
			b.setBackground(initialcolor);
		}
		
	}

	private void displayPlayerShipsPositions() {
	
		List<String>  allPositions;
		
		if(player1Checked) {
			allPositions = player1.getAllPositionsOfShips();
		
		}else {
			allPositions = player2.getAllPositionsOfShips();	
		}
		
		putPlayerShipsIntoMainGrid(allPositions);
	}
	
	private void putPlayerShipsIntoMainGrid(List<String> allPos) {
		
		for(JButton b : listOfGridButtons) {
			if(allPos.contains(b.getText())) {
				b.setBackground(Color.GREEN);
					}
			}
		
	}

	private void setHittedOnTarget(JButton button) {
		
		List<String> playerGird;
		
		if(player1Checked) {
		
			playerGird = player1.addHittedOnTargetPosition(button.getText());
		
		}else {	
		
			playerGird = player2.addHittedOnTargetPosition(button.getText());
		}
		
		setCurrentPlayerHitsIntoMainGrid(playerGird);
		
	}
	
	private void setCurrentPlayerHitsIntoMainGrid(List<String> playerGrid) {
		
		for(String p : playerGrid) {
		
			for(JButton b : listOfGridButtons) {
		
		if(b.getText().equals(p)) {		
				
			b.setBackground(Color.RED);
				}
			}
		}
	}
	
	private void selectingShip(ActionEvent arg) {
		
		if(shipsettingType == 0) {
		switch(shipType) {
		
		case "Carrier": shipsettingType =5;
			break;
		case "Battleship": shipsettingType = 4;
			break;
		case "Destroyer": shipsettingType = 3;
			break;	
		case "Submarine": shipsettingType = 3;
			break;
		case "Patrol Boat": shipsettingType = 2;
			break;
		default: 
			 textarea.setText("select type of\nship");
			return;
		}
	}
		if(player1Checked==true && shipsettingType>0) {
			System.out.println("shipsettingType: " + shipsettingType);
			jcbox.setEnabled(false);
			shipsettingType = player1.setShip(arg ,shipsettingType);
	
		}
		
		if(player2Checked == true && shipsettingType>0) {
			System.out.println("shipsettingType: " + shipsettingType);
			jcbox.setEnabled(false);
			shipsettingType = player2.setShip(arg ,shipsettingType);
	
		}
		
		if(player1Checked == true && shipsettingType == 0) {
		player1.addPositionToShip(shipType);
		player1.clearPosition();
		jcbox.removeItem(jcbox.getSelectedItem());
		jcbox.setEnabled(true);
		}
		
		if(player2Checked == true && shipsettingType == 0) {
			player2.addPositionToShip(shipType);
			player2.clearPosition();
			jcbox.removeItem(jcbox.getSelectedItem());
			jcbox.setEnabled(true);	
		}
			
		if(jcbox.getItemCount()==0) {
			
			if(player1Checked) {
				shipSelectlabel.setText("Plaer 1 has\nbeen set");
				player1Set = true;
				setPlayer1.setBackground(initialcolor);
				setPlayer1.setEnabled(false);
				}
			
				if(player2Checked) {
					shipSelectlabel.setText("Plaer 2 has\nbeen set");
					player2Set = true;
					setPlayer2.setBackground(initialcolor);
					setPlayer2.setEnabled(false);
					}
				
			initJComboBoxWithShipIteams();
			textarea.setText("");
			player1Checked=false;
			player2Checked=false;
			changeColorOfListOfGridButtons();
	
			shipsettingType=0;
			shipType="";
			
			}
		
		if(player1Set && player2Set) {
			System.out.println("nisde check if all players set");
			
			System.out.println(player1Checked + " "+ player2Checked + " " + shipsettingType + " " + shipType);
			startButton.setEnabled(true);
			shipRestButton.setEnabled(false);
			jcbox.setEnabled(false);
	
		}

	}
	private void clearAllSet() {
		
			start=false;
			player1Set = false;
			player2Set = false;
			player1Checked=false;
			player2Checked=false;
			player1.clearAll();
			shipSelectlabel.setText("");
			textarea.setText("");
			removeAllIteamsInJComboBox();
			initJComboBoxWithShipIteams();
			player1ButtonSetDefaultColor();
			changeColorOfListOfGridButtons();
			shipsettingType=0;
			shipType="";
			setPlayer1.setEnabled(true);
			jcbox.setEnabled(true);
			player1.displayAllSetedShipsToConsole();
			
			player2.clearAll();
			player2ButtonSetDefaultColor();
			
			setPlayer2.setEnabled(true);
			player2.displayAllSetedShipsToConsole();
			
	}

	private boolean checkListOfGirdButtons() {
	
		for(JButton b : listOfGridButtons) {
			
			if(b.getBackground().equals(Color.GREEN)) {
			return true;
			}
		}
		return false;
	}
	
	private void  clearAllAction() {
		
		if(checkListOfGirdButtons()) {
			clearAllSet();
		}
		
	}
	
	public void changeColorOfListOfGridButtons() {
		Iterator<JButton> itr =listOfGridButtons.iterator();
	
		while(itr.hasNext()) {
		
			itr.next().setBackground(initialcolor);
		}
	}
	
	public void player1ButtonSetDefaultColor(){
		setPlayer1.setBackground(initialcolor);
	}
	
	public void player2ButtonSetDefaultColor(){
		setPlayer2.setBackground(initialcolor);
	}
	
}
