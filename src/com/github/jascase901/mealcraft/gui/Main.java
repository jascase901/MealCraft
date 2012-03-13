package com.github.jascase901.mealcraft.gui;
import com.github.jascase901.mealcraft.db.IngredientDb;
import com.github.jascase901.mealcraft.db.ProfileDb;
import com.github.jascase901.mealcraft.db.RecipeBookDb;
import com.github.jascase901.mealcraft.db.RecipeToIngredientsDb;
import com.github.jascase901.mealcraft.system.Ingredient;
import com.github.jascase901.mealcraft.usr.Profile;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JMenu;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JButton;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import java.awt.CardLayout;
import java.awt.Font;

import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JComboBox;
import javax.swing.JTextField;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.border.BevelBorder;
import javax.swing.border.LineBorder;
import java.awt.Color;
import javax.swing.border.SoftBevelBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.Document;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JTable;
import javax.swing.JScrollPane;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class Main extends JFrame {

	private JPanel card;
	private JTextField name;
	private JTextField pantry;
	private String inputProfileName = "";
	private String inputPantryName = "";
	private String currentProfile = "";
	private JLabel lblProfile;
	private JTable pantryTable;
	private JTable recipeBookTable;
	private JLabel lblNewLabel = new JLabel();
	private JButton nextButton = new JButton("Create my Profile!");
	private JDatePicker panel_1 = new JDatePicker();
	private JScrollPane scrollPane = new JScrollPane();
	private String[] profiles = {};
	private String[] units = {"lbs","liters"};
	private JComboBox comboBox = new JComboBox();
	private JButton nextButton2 = new JButton("GO !!");
	private JTextField txtEgBacon;
	private JTextField txtEg;
	private JTextField txtEg_1;
	private JTextField textField_Price;
	private IngredientDb ingr;
	private JComboBox comboBox_1;
	private boolean firstClickName = true;
	private boolean firstClickQuantity = true;
	private boolean firstClickCalorie = true;
	private boolean firstClickPrice = true;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Main frame = new Main();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public Main() {
		setResizable(false);
		setTitle("MealCraft v0.01");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1250, 750);
		
		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);
		
		JMenu mnFile = new JMenu("File");
		menuBar.add(mnFile);
		
		JMenuItem mntmExit = new JMenuItem("Exit");
		mntmExit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				System.exit(0);
			}
		});
		mnFile.add(mntmExit);
		card = new JPanel();
		card.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(card);
		card.setLayout(new CardLayout(0, 0));
		
		JPanel profile = new JPanel();
		card.add(profile, "profile");
		
		JButton btnNewButton = new JButton("New Profile...");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt){
		        CardLayout cl = (CardLayout)(card.getLayout());
		        cl.show(card, "newProfile");
			}
		});
		btnNewButton.setFont(new Font("Comic Sans MS", Font.PLAIN, 18));
		
		JButton btnNewButton_1 = new JButton("Choose Profile...");
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				redoPrSelect();
				comboBox.revalidate();
				comboBox.validate();
				comboBox.repaint();
		        CardLayout c2 = (CardLayout)(card.getLayout());
		        c2.show(card, "profileSelect");
			}
		});
		btnNewButton_1.setFont(new Font("Comic Sans MS", Font.PLAIN, 18));
		GroupLayout gl_profile = new GroupLayout(profile);
		gl_profile.setHorizontalGroup(
			gl_profile.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_profile.createSequentialGroup()
					.addGap(366)
					.addGroup(gl_profile.createParallelGroup(Alignment.TRAILING, false)
						.addComponent(btnNewButton, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
						.addComponent(btnNewButton_1, Alignment.LEADING, GroupLayout.PREFERRED_SIZE, 493, GroupLayout.PREFERRED_SIZE))
					.addContainerGap(365, Short.MAX_VALUE))
		);
		gl_profile.setVerticalGroup(
			gl_profile.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_profile.createSequentialGroup()
					.addGap(273)
					.addComponent(btnNewButton, GroupLayout.PREFERRED_SIZE, 54, GroupLayout.PREFERRED_SIZE)
					.addGap(27)
					.addComponent(btnNewButton_1, GroupLayout.PREFERRED_SIZE, 54, GroupLayout.PREFERRED_SIZE)
					.addContainerGap(273, Short.MAX_VALUE))
		);
		profile.setLayout(gl_profile);
		
		JPanel newProfile = new JPanel();
		card.add(newProfile, "newProfile");
		
		JLabel lblProfileName = new JLabel("Profile Name:");
		lblProfileName.setFont(new Font("Comic Sans MS", Font.PLAIN, 15));
		
		name = new JTextField();
		name.setFont(new Font("Comic Sans MS", Font.PLAIN, 13));
		name.setColumns(10);
		
		Document textFieldDoc = name.getDocument();
		textFieldDoc.addDocumentListener(new DocumentListener() {
		   public void changedUpdate(DocumentEvent e) {
		      updated(e);
		   }
		   public void insertUpdate(DocumentEvent e) {
		      updated(e);
		   }
		   public void removeUpdate(DocumentEvent e) {
		      updated(e);
		   }
		   private void updated(DocumentEvent e) {
		      boolean enable = e.getDocument().getLength() > 0;
		      nextButton.setEnabled(enable);
		   }
		});
		
		JLabel lblPantryName = new JLabel("Pantry Name:");
		lblPantryName.setFont(new Font("Comic Sans MS", Font.PLAIN, 15));
		
		pantry = new JTextField();
		pantry.setFont(new Font("Comic Sans MS", Font.PLAIN, 13));
		pantry.setColumns(10);
		
		JButton backButton = new JButton("<< Back");
		backButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
		        CardLayout c3 = (CardLayout)(card.getLayout());
		        c3.show(card, "profile");
			}
		});
		
		nextButton.setEnabled(false);

		nextButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				inputProfileName = name.getText();
				inputPantryName = pantry.getText();
				Profile myProfile= new Profile(inputProfileName, inputPantryName);
				
				currentProfile = name.getText();
				lblNewLabel.setText(currentProfile);
				lblNewLabel.repaint();
				
				try {
				ProfileDb myProfileDb = new ProfileDb();
				myProfileDb.addProfile(myProfile);
				System.out.println(myProfileDb.getName(myProfile.getName()));
				
				}
				catch(Exception ex){
					JDialog err = new JDialog();
					err.setEnabled(true);
					
				}
				
				try{
					ProfileDb myProfileDb = new ProfileDb();
					profiles = myProfileDb.toStringArray();
					System.out.println("YAY");
				}
				catch(Exception a){
					a.printStackTrace();
					System.out.println("GAH");
					
				}

		        CardLayout c4 = (CardLayout)(card.getLayout());
		        c4.show(card, "mainMenu");
			}
		});
		GroupLayout gl_newProfile = new GroupLayout(newProfile);
		gl_newProfile.setHorizontalGroup(
			gl_newProfile.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_newProfile.createSequentialGroup()
					.addGap(326)
					.addGroup(gl_newProfile.createParallelGroup(Alignment.TRAILING, false)
						.addComponent(lblPantryName, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
						.addComponent(lblProfileName, Alignment.LEADING, GroupLayout.PREFERRED_SIZE, 95, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addGroup(gl_newProfile.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_newProfile.createSequentialGroup()
							.addGap(63)
							.addComponent(backButton, GroupLayout.PREFERRED_SIZE, 136, GroupLayout.PREFERRED_SIZE)
							.addGap(63)
							.addComponent(nextButton, GroupLayout.PREFERRED_SIZE, 136, GroupLayout.PREFERRED_SIZE))
						.addComponent(name, GroupLayout.PREFERRED_SIZE, 467, GroupLayout.PREFERRED_SIZE)
						.addComponent(pantry, GroupLayout.PREFERRED_SIZE, 467, GroupLayout.PREFERRED_SIZE))
					.addContainerGap(326, Short.MAX_VALUE))
		);
		gl_newProfile.setVerticalGroup(
			gl_newProfile.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_newProfile.createSequentialGroup()
					.addGap(297)
					.addGroup(gl_newProfile.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblProfileName, GroupLayout.PREFERRED_SIZE, 30, GroupLayout.PREFERRED_SIZE)
						.addComponent(name, GroupLayout.PREFERRED_SIZE, 27, GroupLayout.PREFERRED_SIZE))
					.addGap(28)
					.addGroup(gl_newProfile.createParallelGroup(Alignment.LEADING)
						.addComponent(lblPantryName, GroupLayout.PREFERRED_SIZE, 28, GroupLayout.PREFERRED_SIZE)
						.addComponent(pantry, GroupLayout.PREFERRED_SIZE, 27, GroupLayout.PREFERRED_SIZE))
					.addGap(33)
					.addGroup(gl_newProfile.createParallelGroup(Alignment.BASELINE)
						.addComponent(nextButton, GroupLayout.PREFERRED_SIZE, 49, GroupLayout.PREFERRED_SIZE)
						.addComponent(backButton, GroupLayout.PREFERRED_SIZE, 49, GroupLayout.PREFERRED_SIZE))
					.addContainerGap(216, Short.MAX_VALUE))
		);
		newProfile.setLayout(gl_newProfile);
		
		JPanel profileSelect = new JPanel();
		card.add(profileSelect, "profileSelect");
		
		JLabel lblSelectProfile = new JLabel("Select Profile...");
		lblSelectProfile.setFont(new Font("Comic Sans MS", Font.PLAIN, 15));
		redoPrSelect();
		/*
	try{
		ProfileDb myProfileDb = new ProfileDb();
		profiles = myProfileDb.toStringArray();
		System.out.println("YAY");
	}
	catch(Exception a){
		a.printStackTrace();
		System.out.println("GAH");
		
	}
		//WE ADDDED THIS
	
	DefaultComboBoxModel model = new DefaultComboBoxModel(profiles);
		comboBox.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if((String)comboBox.getSelectedItem() != "" && (String)comboBox.getSelectedItem() != null){
					nextButton2.setEnabled(true);
				}
				if((String)comboBox.getSelectedItem() == "" || (String)comboBox.getSelectedItem() == null){
					nextButton2.setEnabled(false);
				}
			}
		});
	
		comboBox.setModel(model);
		comboBox.setFont(new Font("Comic Sans MS", Font.PLAIN, 13));
		
		*/
		
		nextButton2.setEnabled(false);
		
		
		nextButton2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				currentProfile = (String)comboBox.getSelectedItem();
				lblProfile= new JLabel(currentProfile);
								
				CardLayout c5 = (CardLayout)(card.getLayout());
				c5.show(card, "mainMenu");
				
		        System.out.println(currentProfile);
		        lblNewLabel.setText(currentProfile);
		        lblNewLabel.repaint();
			}
		});
		nextButton2.setFont(new Font("Comic Sans MS", Font.PLAIN, 15));
		
		JButton btnBack = new JButton("<< Back");
		btnBack.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
		        CardLayout c3 = (CardLayout)(card.getLayout());
		        c3.show(card, "profile");
			}
		});
		btnBack.setFont(new Font("Comic Sans MS", Font.PLAIN, 15));
		GroupLayout gl_profileSelect = new GroupLayout(profileSelect);
		gl_profileSelect.setHorizontalGroup(
			gl_profileSelect.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_profileSelect.createSequentialGroup()
					.addGroup(gl_profileSelect.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_profileSelect.createSequentialGroup()
							.addGap(552)
							.addComponent(lblSelectProfile, GroupLayout.PREFERRED_SIZE, 120, GroupLayout.PREFERRED_SIZE))
						.addGroup(gl_profileSelect.createSequentialGroup()
							.addGap(500)
							.addComponent(comboBox, GroupLayout.PREFERRED_SIZE, 224, GroupLayout.PREFERRED_SIZE))
						.addGroup(gl_profileSelect.createSequentialGroup()
							.addGap(507)
							.addComponent(btnBack, GroupLayout.PREFERRED_SIZE, 101, GroupLayout.PREFERRED_SIZE)
							.addGap(18)
							.addComponent(nextButton2, GroupLayout.PREFERRED_SIZE, 101, GroupLayout.PREFERRED_SIZE)))
					.addContainerGap(510, Short.MAX_VALUE))
		);
		gl_profileSelect.setVerticalGroup(
			gl_profileSelect.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_profileSelect.createSequentialGroup()
					.addGap(202)
					.addComponent(lblSelectProfile, GroupLayout.PREFERRED_SIZE, 33, GroupLayout.PREFERRED_SIZE)
					.addGap(42)
					.addComponent(comboBox, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
					.addGap(47)
					.addGroup(gl_profileSelect.createParallelGroup(Alignment.LEADING)
						.addComponent(nextButton2, GroupLayout.PREFERRED_SIZE, 73, GroupLayout.PREFERRED_SIZE)
						.addComponent(btnBack, GroupLayout.PREFERRED_SIZE, 73, GroupLayout.PREFERRED_SIZE))
					.addContainerGap(269, Short.MAX_VALUE))
		);
		profileSelect.setLayout(gl_profileSelect);
		
		JPanel mainMenu = new JPanel();
		card.add(mainMenu, "mainMenu");
		
		JPanel tabSelection = new JPanel();
		tabSelection.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		
		JPanel panel = new JPanel();
		panel.setBorder(new LineBorder(new Color(0, 0, 0)));
		
		final JPanel TAB_BODY = new JPanel();
		TAB_BODY.setBorder(new SoftBevelBorder(BevelBorder.RAISED, null, null, null, null));
		TAB_BODY.setLayout(new CardLayout(0, 0));
		GroupLayout gl_mainMenu = new GroupLayout(mainMenu);
		gl_mainMenu.setHorizontalGroup(
			gl_mainMenu.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_mainMenu.createSequentialGroup()
					.addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
					.addGroup(gl_mainMenu.createParallelGroup(Alignment.LEADING)
						.addGroup(Alignment.TRAILING, gl_mainMenu.createSequentialGroup()
							.addComponent(tabSelection, GroupLayout.PREFERRED_SIZE, 184, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.RELATED, 10, Short.MAX_VALUE)
							.addComponent(TAB_BODY, GroupLayout.PREFERRED_SIZE, 1010, GroupLayout.PREFERRED_SIZE))
						.addComponent(panel, Alignment.TRAILING, GroupLayout.PREFERRED_SIZE, 1204, GroupLayout.PREFERRED_SIZE))
					.addContainerGap())
		);
		gl_mainMenu.setVerticalGroup(
			gl_mainMenu.createParallelGroup(Alignment.LEADING)
				.addGroup(Alignment.TRAILING, gl_mainMenu.createSequentialGroup()
					.addContainerGap()
					.addComponent(panel, GroupLayout.PREFERRED_SIZE, 41, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED, 16, Short.MAX_VALUE)
					.addGroup(gl_mainMenu.createParallelGroup(Alignment.TRAILING)
						.addComponent(TAB_BODY, GroupLayout.PREFERRED_SIZE, 602, GroupLayout.PREFERRED_SIZE)
						.addComponent(tabSelection, GroupLayout.PREFERRED_SIZE, 602, GroupLayout.PREFERRED_SIZE))
					.addContainerGap())
		);
		
		JPanel tabBodyMain = new JPanel();
		TAB_BODY.add(tabBodyMain, "tabBodyMain");
		
		JLabel lblMainPage = new JLabel("main page");
		GroupLayout gl_tabBodyMain = new GroupLayout(tabBodyMain);
		gl_tabBodyMain.setHorizontalGroup(
			gl_tabBodyMain.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_tabBodyMain.createSequentialGroup()
					.addContainerGap()
					.addComponent(lblMainPage)
					.addContainerGap(945, Short.MAX_VALUE))
		);
		gl_tabBodyMain.setVerticalGroup(
			gl_tabBodyMain.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_tabBodyMain.createSequentialGroup()
					.addContainerGap()
					.addComponent(lblMainPage)
					.addContainerGap(571, Short.MAX_VALUE))
		);
		tabBodyMain.setLayout(gl_tabBodyMain);
		
		JPanel tabBodyPantry = new JPanel();
		TAB_BODY.add(tabBodyPantry, "tabBodyPantry");
		redoPTable();
	/*	
		public void redoPTable(){
			pantryTable = new JTable(50, 4);
			pantryTable.setEnabled(false);
			// I AM ADDING THIS
			try { 
				IngredientDb myIngredientDb= new IngredientDb();
				int count  = myIngredientDb.count();
			
			
				String[] names=myIngredientDb.namesArray();
				String[] amounts= myIngredientDb.amountsArray();
				String[] calories=myIngredientDb.caloriesArray();
				String[] units=myIngredientDb.unitsArray();
				String[] prices=myIngredientDb.pricesArray();
				for(int i=0;i<count;i++){
					pantryTable.setValueAt(names[i], i,0);
					
					pantryTable.setValueAt(amounts[i]+" "+units[i],i,1);
					
					pantryTable.setValueAt(calories[i],i,2);
			
					pantryTable.setValueAt("$ "+prices[i], i, 3);
				}	
				myIngredientDb.close();
			}
			catch(Exception ect){
					
			}
			scrollPane.setViewportView(pantryTable);
		}
		*/
		JLabel lblNewLabel_1 = new JLabel("Ingredients\r\n");
		lblNewLabel_1.setFont(new Font("Comic Sans MS", Font.PLAIN, 14));
		
		JLabel lblQuantity = new JLabel("Quantity");
		lblQuantity.setFont(new Font("Comic Sans MS", Font.PLAIN, 14));
		
		JLabel lblCalories = new JLabel("Calories\r\n");
		lblCalories.setFont(new Font("Comic Sans MS", Font.PLAIN, 14));
		
		JLabel lblPrice = new JLabel("Price\r\n");
		lblPrice.setFont(new Font("Comic Sans MS", Font.PLAIN, 14));
		
		JPanel panel_2 = new JPanel();
		panel_2.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		
		GroupLayout gl_tabBodyPantry = new GroupLayout(tabBodyPantry);
		gl_tabBodyPantry.setHorizontalGroup(
			gl_tabBodyPantry.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_tabBodyPantry.createSequentialGroup()
					.addGap(89)
					.addComponent(lblNewLabel_1, GroupLayout.PREFERRED_SIZE, 93, GroupLayout.PREFERRED_SIZE)
					.addGap(177)
					.addComponent(lblQuantity)
					.addGap(189)
					.addComponent(lblCalories, GroupLayout.PREFERRED_SIZE, 58, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED, 179, Short.MAX_VALUE)
					.addComponent(lblPrice, GroupLayout.PREFERRED_SIZE, 58, GroupLayout.PREFERRED_SIZE)
					.addGap(103))
				.addGroup(gl_tabBodyPantry.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_tabBodyPantry.createParallelGroup(Alignment.TRAILING, false)
						.addComponent(panel_2, Alignment.LEADING, 0, 0, Short.MAX_VALUE)
						.addComponent(scrollPane, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 972, Short.MAX_VALUE))
					.addGap(23))
		);
		gl_tabBodyPantry.setVerticalGroup(
			gl_tabBodyPantry.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_tabBodyPantry.createSequentialGroup()
					.addGap(19)
					.addGroup(gl_tabBodyPantry.createParallelGroup(Alignment.LEADING)
						.addComponent(lblPrice, GroupLayout.PREFERRED_SIZE, 21, GroupLayout.PREFERRED_SIZE)
						.addGroup(gl_tabBodyPantry.createParallelGroup(Alignment.BASELINE)
							.addComponent(lblCalories, GroupLayout.PREFERRED_SIZE, 21, GroupLayout.PREFERRED_SIZE)
							.addComponent(lblQuantity)
							.addComponent(lblNewLabel_1, GroupLayout.PREFERRED_SIZE, 29, GroupLayout.PREFERRED_SIZE)))
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addComponent(scrollPane, GroupLayout.PREFERRED_SIZE, 384, GroupLayout.PREFERRED_SIZE)
					.addGap(18)
					.addComponent(panel_2, GroupLayout.PREFERRED_SIZE, 123, GroupLayout.PREFERRED_SIZE)
					.addContainerGap(12, Short.MAX_VALUE))
		);
		
		txtEgBacon = new JTextField();
		txtEgBacon.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				if(firstClickName){
					txtEgBacon.setText("");
					firstClickName = false;
				}
			}
		});
		txtEgBacon.setText("Bacon");
		txtEgBacon.setFont(new Font("Comic Sans MS", Font.PLAIN, 13));
		txtEgBacon.setColumns(10);
		
		JButton pantryAddBtn = new JButton("Add");
		pantryAddBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				try {
					ingr = new IngredientDb();
					Ingredient ingredient = new Ingredient(txtEgBacon.getText(),Integer.parseInt(txtEg_1.getText()),Double.parseDouble(textField_Price.getText()));
					ingr.addIngredient(ingredient, Double.parseDouble(txtEg.getText()),(String)comboBox_1.getSelectedItem());
					ingr.close();
				} catch (Exception e) {
					e.printStackTrace();
				}
				redoPTable();
				pantryTable.revalidate();
				pantryTable.repaint();
				scrollPane.setViewportView(pantryTable);
				scrollPane.revalidate();
				scrollPane.repaint();
				
			}
		});
		pantryAddBtn.setFont(new Font("Comic Sans MS", Font.PLAIN, 14));
		
		txtEg = new JTextField();
		txtEg.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if(firstClickQuantity){
					txtEg.setText("");
					firstClickQuantity = false;
				}
			}
		});
		txtEg.setText("4.2");
		txtEg.setFont(new Font("Comic Sans MS", Font.PLAIN, 13));
		txtEg.setColumns(10);
		
		txtEg_1 = new JTextField();
		txtEg_1.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if(firstClickCalorie){
					txtEg_1.setText("");
					firstClickCalorie = false;
				}
			}
		});
		txtEg_1.setText("420");
		txtEg_1.setFont(new Font("Comic Sans MS", Font.PLAIN, 13));
		txtEg_1.setColumns(10);
		
		textField_Price = new JTextField();
		textField_Price.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if(firstClickPrice){
					textField_Price.setText("");
					firstClickPrice = false;
				}
			}
		});
		textField_Price.setText("19.99");
		textField_Price.setFont(new Font("Comic Sans MS", Font.PLAIN, 13));
		textField_Price.setColumns(10);
		
		comboBox_1 = new JComboBox(units);
		
		JButton btnRemove = new JButton("Remove");
		btnRemove.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				try {
					ingr = new IngredientDb();
					ingr.removeKey("name", "pantry", txtEgBacon.getText());
					ingr.close();
				} catch (Exception e) {
					e.printStackTrace();
				}
				redoPTable();
				pantryTable.revalidate();
				pantryTable.repaint();
				scrollPane.setViewportView(pantryTable);
				scrollPane.revalidate();
				scrollPane.repaint();
			}
		});
		btnRemove.setFont(new Font("Comic Sans MS", Font.PLAIN, 14));
		
		GroupLayout gl_panel_2 = new GroupLayout(panel_2);
		gl_panel_2.setHorizontalGroup(
			gl_panel_2.createParallelGroup(Alignment.TRAILING)
				.addGroup(gl_panel_2.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_panel_2.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_panel_2.createSequentialGroup()
							.addComponent(txtEgBacon, GroupLayout.PREFERRED_SIZE, 212, GroupLayout.PREFERRED_SIZE)
							.addGap(29)
							.addComponent(txtEg, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(comboBox_1, GroupLayout.PREFERRED_SIZE, 105, GroupLayout.PREFERRED_SIZE)
							.addGap(30)
							.addComponent(txtEg_1, GroupLayout.PREFERRED_SIZE, 211, GroupLayout.PREFERRED_SIZE)
							.addGap(37)
							.addComponent(textField_Price, GroupLayout.PREFERRED_SIZE, 84, GroupLayout.PREFERRED_SIZE)
							.addGap(18)
							.addComponent(pantryAddBtn, GroupLayout.PREFERRED_SIZE, 120, Short.MAX_VALUE))
						.addComponent(btnRemove, Alignment.TRAILING, GroupLayout.PREFERRED_SIZE, 120, GroupLayout.PREFERRED_SIZE))
					.addContainerGap())
		);
		gl_panel_2.setVerticalGroup(
			gl_panel_2.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel_2.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_panel_2.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_panel_2.createSequentialGroup()
							.addGroup(gl_panel_2.createParallelGroup(Alignment.BASELINE)
								.addComponent(txtEg_1, GroupLayout.PREFERRED_SIZE, 31, GroupLayout.PREFERRED_SIZE)
								.addComponent(textField_Price, GroupLayout.PREFERRED_SIZE, 31, GroupLayout.PREFERRED_SIZE)
								.addComponent(pantryAddBtn))
							.addPreferredGap(ComponentPlacement.RELATED, 48, Short.MAX_VALUE)
							.addComponent(btnRemove, GroupLayout.PREFERRED_SIZE, 29, GroupLayout.PREFERRED_SIZE))
						.addGroup(gl_panel_2.createParallelGroup(Alignment.TRAILING, false)
							.addComponent(txtEg, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 31, Short.MAX_VALUE)
							.addComponent(comboBox_1, Alignment.LEADING)
							.addComponent(txtEgBacon)))
					.addContainerGap())
		);
		panel_2.setLayout(gl_panel_2);
		tabBodyPantry.setLayout(gl_tabBodyPantry);
		
		JPanel tabBodyRecipe = new JPanel();
		TAB_BODY.add(tabBodyRecipe, "tabBodyRecipe");
		
		JPanel recipeSelector = new JPanel();
		
		recipeBookTable = new JTable(600, 3);
		GroupLayout gl_tabBodyRecipe = new GroupLayout(tabBodyRecipe);
		gl_tabBodyRecipe.setHorizontalGroup(
			gl_tabBodyRecipe.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_tabBodyRecipe.createSequentialGroup()
					.addContainerGap()
					.addComponent(recipeSelector, GroupLayout.PREFERRED_SIZE, 274, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addComponent(recipeBookTable, GroupLayout.PREFERRED_SIZE, 702, GroupLayout.PREFERRED_SIZE)
					.addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
		);
		gl_tabBodyRecipe.setVerticalGroup(
			gl_tabBodyRecipe.createParallelGroup(Alignment.TRAILING)
				.addGroup(gl_tabBodyRecipe.createSequentialGroup()
					.addContainerGap(61, Short.MAX_VALUE)
					.addGroup(gl_tabBodyRecipe.createParallelGroup(Alignment.LEADING)
						.addComponent(recipeBookTable, GroupLayout.PREFERRED_SIZE, 511, GroupLayout.PREFERRED_SIZE)
						.addComponent(recipeSelector, GroupLayout.PREFERRED_SIZE, 511, GroupLayout.PREFERRED_SIZE))
					.addGap(24))
		);
		
		//I ADDED THIS
		String[] recipes = { "1","2","3","4"};
		String[] quantity = { "1","2","3","4"};
		String[] ingredients = { "1","2","3","4"};
		try{
			RecipeBookDb rb = new RecipeBookDb();
			recipes = rb.catArray("name");
			rb.close();
			RecipeToIngredientsDb sleep = new RecipeToIngredientsDb();
			quantity=sleep.catArray("ingredient_quantity");
			sleep.close();
			
			
			
		}
		catch(Exception e){}
		finally{}
		
		JComboBox recipeList = new JComboBox(recipes);
		recipeList.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
		        JComboBox cb = (JComboBox)e.getSource();
		        String chosenRecipe = (String)cb.getSelectedItem();
		        System.out.println(chosenRecipe);
				String[] ingredients = { "-1","-2","-3","-4"};

		     try{
		    	 RecipeToIngredientsDb sleep = new RecipeToIngredientsDb();
					ingredients =sleep.getIngredientsThatRequire(chosenRecipe);
					
					sleep.close();
		     }
		     catch(Exception ext){System.out.println("failed  combobox"+ext.getMessage());}
		     int i=0;
		     for(String each: ingredients){
		     
		        recipeBookTable.setValueAt(each, i, 0);
		    	 i++;

		        
		     }   
			}
		});
		
		JButton viewRecipeBtn = new JButton("New button");
		GroupLayout gl_recipeSelector = new GroupLayout(recipeSelector);
		gl_recipeSelector.setHorizontalGroup(
			gl_recipeSelector.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_recipeSelector.createSequentialGroup()
					.addGap(19)
					.addComponent(recipeList, GroupLayout.PREFERRED_SIZE, 237, GroupLayout.PREFERRED_SIZE)
					.addContainerGap(18, Short.MAX_VALUE))
				.addGroup(Alignment.TRAILING, gl_recipeSelector.createSequentialGroup()
					.addContainerGap(58, Short.MAX_VALUE)
					.addComponent(viewRecipeBtn, GroupLayout.PREFERRED_SIZE, 164, GroupLayout.PREFERRED_SIZE)
					.addGap(52))
		);
		gl_recipeSelector.setVerticalGroup(
			gl_recipeSelector.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_recipeSelector.createSequentialGroup()
					.addGap(41)
					.addComponent(recipeList, GroupLayout.PREFERRED_SIZE, 52, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED, 277, Short.MAX_VALUE)
					.addComponent(viewRecipeBtn, GroupLayout.PREFERRED_SIZE, 92, GroupLayout.PREFERRED_SIZE)
					.addGap(49))
		);
		recipeSelector.setLayout(gl_recipeSelector);

		tabBodyRecipe.setLayout(gl_tabBodyRecipe);
		
		JPanel tabBodyCalendar = new JPanel();
		//CALENDAR STUFF
		
		TAB_BODY.add(tabBodyCalendar, "tabBodyCalendar");
		
		GroupLayout gl_tabBodyCalendar = new GroupLayout(tabBodyCalendar);
		gl_tabBodyCalendar.setHorizontalGroup(
			gl_tabBodyCalendar.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_tabBodyCalendar.createSequentialGroup()
					.addContainerGap()
					.addComponent(panel_1, GroupLayout.PREFERRED_SIZE, 982, GroupLayout.PREFERRED_SIZE)
					.addContainerGap(12, Short.MAX_VALUE))
		);
		gl_tabBodyCalendar.setVerticalGroup(
			gl_tabBodyCalendar.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_tabBodyCalendar.createSequentialGroup()
					.addContainerGap()
					.addComponent(panel_1, GroupLayout.PREFERRED_SIZE, 576, GroupLayout.PREFERRED_SIZE)
					.addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
		);
		tabBodyCalendar.setLayout(gl_tabBodyCalendar);
		
		
		JPanel tabBodyTools = new JPanel();
		TAB_BODY.add(tabBodyTools, "tabBodyTools");
		
		JLabel lblTools = new JLabel("Tools");
		GroupLayout gl_tabBodyTools = new GroupLayout(tabBodyTools);
		gl_tabBodyTools.setHorizontalGroup(
			gl_tabBodyTools.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_tabBodyTools.createSequentialGroup()
					.addGap(40)
					.addComponent(lblTools)
					.addContainerGap(918, Short.MAX_VALUE))
		);
		gl_tabBodyTools.setVerticalGroup(
			gl_tabBodyTools.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_tabBodyTools.createSequentialGroup()
					.addGap(35)
					.addComponent(lblTools)
					.addContainerGap(547, Short.MAX_VALUE))
		);
		tabBodyTools.setLayout(gl_tabBodyTools);
		
		JPanel tabBodyShoppingList = new JPanel();
		TAB_BODY.add(tabBodyShoppingList, "tabBodyShoppingList");
		
		JLabel lblShoppingList = new JLabel("shopping list");
		GroupLayout gl_tabBodyShoppingList = new GroupLayout(tabBodyShoppingList);
		gl_tabBodyShoppingList.setHorizontalGroup(
			gl_tabBodyShoppingList.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_tabBodyShoppingList.createSequentialGroup()
					.addGap(29)
					.addComponent(lblShoppingList)
					.addContainerGap(916, Short.MAX_VALUE))
		);
		gl_tabBodyShoppingList.setVerticalGroup(
			gl_tabBodyShoppingList.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_tabBodyShoppingList.createSequentialGroup()
					.addGap(27)
					.addComponent(lblShoppingList)
					.addContainerGap(555, Short.MAX_VALUE))
		);
		tabBodyShoppingList.setLayout(gl_tabBodyShoppingList);
		
		JButton logout = new JButton("Log out/Switch");
		logout.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
		        CardLayout logout = (CardLayout)(card.getLayout());
		        logout.show(card, "profile");
				
			}
		});
		logout.setEnabled(true);
		
		lblProfile = new JLabel("Profile:");
		lblProfile.setFont(new Font("Comic Sans MS", Font.PLAIN, 13));
		System.out.println(currentProfile);
		//I added This
		//here
		lblNewLabel.setFont(new Font("Comic Sans MS", Font.PLAIN, 14));
		lblNewLabel.setText(currentProfile);
		GroupLayout gl_panel = new GroupLayout(panel);
		gl_panel.setHorizontalGroup(
			gl_panel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel.createSequentialGroup()
					.addContainerGap()
					.addComponent(lblProfile, GroupLayout.PREFERRED_SIZE, 56, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(lblNewLabel, GroupLayout.PREFERRED_SIZE, 163, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED, 816, Short.MAX_VALUE)
					.addComponent(logout, GroupLayout.PREFERRED_SIZE, 151, GroupLayout.PREFERRED_SIZE))
		);
		gl_panel.setVerticalGroup(
			gl_panel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_panel.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblProfile, GroupLayout.PREFERRED_SIZE, 21, GroupLayout.PREFERRED_SIZE)
						.addComponent(lblNewLabel, GroupLayout.PREFERRED_SIZE, 21, GroupLayout.PREFERRED_SIZE)))
				.addComponent(logout, GroupLayout.DEFAULT_SIZE, 39, Short.MAX_VALUE)
		);
		panel.setLayout(gl_panel);
		
		JButton tabPantry = new JButton("Pantry");
		tabPantry.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
		        CardLayout toTabBodyPantry = (CardLayout)(TAB_BODY.getLayout());
		        toTabBodyPantry.show(TAB_BODY, "tabBodyPantry");
			}
		});
		tabPantry.setFont(new Font("Comic Sans MS", Font.PLAIN, 14));
		
		JButton tabRecipe = new JButton("Recipe");
		tabRecipe.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
		        CardLayout toTabBodyRecipe = (CardLayout)(TAB_BODY.getLayout());
		        toTabBodyRecipe.show(TAB_BODY, "tabBodyRecipe");
			}
		});
		tabRecipe.setFont(new Font("Comic Sans MS", Font.PLAIN, 14));
		
		JButton tabCalendar = new JButton("Calendar");
		tabCalendar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
		        CardLayout toTabCalendar = (CardLayout)(TAB_BODY.getLayout());
		        toTabCalendar.show(TAB_BODY, "tabBodyCalendar");
			}
		});
		tabCalendar.setFont(new Font("Comic Sans MS", Font.PLAIN, 14));
		
		JButton tabTools = new JButton("Tools");
		tabTools.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
		        CardLayout toTabBodyTools = (CardLayout)(TAB_BODY.getLayout());
		        toTabBodyTools.show(TAB_BODY, "tabBodyTools");
			}
		});
		tabTools.setFont(new Font("Comic Sans MS", Font.PLAIN, 14));
		
		JButton tabShoppingList = new JButton("Shopping List");
		tabShoppingList.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
		        CardLayout toTabBodyShoppingList = (CardLayout)(TAB_BODY.getLayout());
		        toTabBodyShoppingList.show(TAB_BODY, "tabBodyShoppingList");
			}
		});
		tabShoppingList.setFont(new Font("Comic Sans MS", Font.PLAIN, 14));
		
		JButton button_4 = new JButton("New button");
		GroupLayout gl_tabSelection = new GroupLayout(tabSelection);
		gl_tabSelection.setHorizontalGroup(
			gl_tabSelection.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_tabSelection.createSequentialGroup()
					.addGap(24)
					.addGroup(gl_tabSelection.createParallelGroup(Alignment.LEADING)
						.addComponent(button_4, GroupLayout.PREFERRED_SIZE, 129, GroupLayout.PREFERRED_SIZE)
						.addComponent(tabShoppingList, GroupLayout.PREFERRED_SIZE, 129, GroupLayout.PREFERRED_SIZE)
						.addComponent(tabTools, GroupLayout.PREFERRED_SIZE, 129, GroupLayout.PREFERRED_SIZE)
						.addComponent(tabRecipe, GroupLayout.PREFERRED_SIZE, 129, GroupLayout.PREFERRED_SIZE)
						.addComponent(tabPantry, GroupLayout.PREFERRED_SIZE, 129, GroupLayout.PREFERRED_SIZE)
						.addComponent(tabCalendar, GroupLayout.PREFERRED_SIZE, 129, GroupLayout.PREFERRED_SIZE))
					.addContainerGap(27, Short.MAX_VALUE))
		);
		gl_tabSelection.setVerticalGroup(
			gl_tabSelection.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_tabSelection.createSequentialGroup()
					.addGap(61)
					.addComponent(tabPantry, GroupLayout.PREFERRED_SIZE, 45, GroupLayout.PREFERRED_SIZE)
					.addGap(40)
					.addComponent(tabRecipe, GroupLayout.PREFERRED_SIZE, 45, GroupLayout.PREFERRED_SIZE)
					.addGap(40)
					.addComponent(tabCalendar, GroupLayout.PREFERRED_SIZE, 45, GroupLayout.PREFERRED_SIZE)
					.addGap(40)
					.addComponent(tabTools, GroupLayout.PREFERRED_SIZE, 45, GroupLayout.PREFERRED_SIZE)
					.addGap(40)
					.addComponent(tabShoppingList, GroupLayout.PREFERRED_SIZE, 45, GroupLayout.PREFERRED_SIZE)
					.addGap(40)
					.addComponent(button_4, GroupLayout.PREFERRED_SIZE, 45, GroupLayout.PREFERRED_SIZE)
					.addContainerGap(79, Short.MAX_VALUE))
		);
		tabSelection.setLayout(gl_tabSelection);
		mainMenu.setLayout(gl_mainMenu);
	}
	
	public void redoPrSelect(){
		try{
			ProfileDb myProfileDb = new ProfileDb();
			profiles = myProfileDb.toStringArray();
			System.out.println("YAY");
		}
		catch(Exception a){
			a.printStackTrace();
			System.out.println("GAH");
			
		}
			//WE ADDDED THIS
		
		DefaultComboBoxModel model = new DefaultComboBoxModel(profiles);
			comboBox.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					if((String)comboBox.getSelectedItem() != "" && (String)comboBox.getSelectedItem() != null){
						nextButton2.setEnabled(true);
					}
					if((String)comboBox.getSelectedItem() == "" || (String)comboBox.getSelectedItem() == null){
						nextButton2.setEnabled(false);
					}
				}
			});
		
			comboBox.setModel(model);
			comboBox.setFont(new Font("Comic Sans MS", Font.PLAIN, 13));
	}
	
	public void redoPTable(){
		pantryTable = new JTable(50, 4);
		pantryTable.setEnabled(false);
		// I AM ADDING THIS
		try { 
			IngredientDb myIngredientDb= new IngredientDb();
			int count  = myIngredientDb.count();
		
		
			String[] names=myIngredientDb.namesArray();
			String[] amounts= myIngredientDb.amountsArray();
			String[] calories=myIngredientDb.caloriesArray();
			String[] units=myIngredientDb.unitsArray();
			String[] prices=myIngredientDb.pricesArray();
			for(int i=0;i<count;i++){
				pantryTable.setValueAt(names[i], i,0);
				
				pantryTable.setValueAt(amounts[i]+" "+units[i],i,1);
				
				pantryTable.setValueAt(calories[i],i,2);
		
				pantryTable.setValueAt("$ "+prices[i], i, 3);
			}	
			myIngredientDb.close();
		}
		catch(Exception ect){
				
		}
		scrollPane.setViewportView(pantryTable);
	}
}

