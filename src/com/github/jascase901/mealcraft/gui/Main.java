package com.github.jascase901.mealcraft.gui;
import com.github.jascase901.mealcraft.db.IngredientDb;
import com.github.jascase901.mealcraft.db.ProfileDb;
import com.github.jascase901.mealcraft.db.RecipeBookDb;
import com.github.jascase901.mealcraft.db.RecipeToIngredientsDb;
import com.github.jascase901.mealcraft.system.Ingredient;
import com.github.jascase901.mealcraft.system.Quantity;
import com.github.jascase901.mealcraft.system.Recipe;
import com.github.jascase901.mealcraft.system.ShoppingList;
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
import javax.swing.JTextArea;
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
import javax.swing.JList;
import javax.swing.event.ListSelectionListener;
import javax.swing.event.ListSelectionEvent;
import java.awt.Dimension;
import java.util.ArrayList;
import javax.swing.JEditorPane;
import javax.swing.JTabbedPane;

public class Main extends JFrame {

	private JPanel card;
	private JTextField name;
	private JTextField pantry;
	private String inputProfileName = "";
	private String inputPantryName = "";
	private String currentProfile = "";
	private String newRecipe = "";
	private JLabel lblProfile;
	private JTable pantryTable;
	private JLabel lblNewLabel = new JLabel();
	private JButton nextButton = new JButton("Create my Profile!");
	private JDatePicker panel_1 = new JDatePicker();
	private JScrollPane scrollPane = new JScrollPane();
	private String[] profiles = {};
	private String[] units = {"lbs","liters","grams","pieces","oz","tons"};
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
	private JList listORecipe;
	private JScrollPane scrollPane_1;
	private String[] rList;
	private JScrollPane scrollPane_2;
	private JScrollPane scrollPane_3;
	private JTable recipeIngr;
	private JTextArea recipeInstructions;
	private JPanel tabBodyRecipe;
	private JPanel recipeSwitch;
	private JPanel recipeView;
	private JButton removeRcp;
	private JTextField col_ingr_input;
	private JTextField col_quan_input;
	private JTextField col_calo_input;
	private JTextField newRcpName;
	private JButton makeRcp;
	private JLabel lblNewLabel_6;
	private String currRcpName = "";
	private ArrayList<String> col_ingr;
	private ArrayList<String> col_quan;
	private ArrayList<String> col_unit;
	private ArrayList<String> col_calo;
	private JTable aRecipeIngr;
	private JScrollPane scrollPane_4;
	private JComboBox comboBox_2;
	private JButton addToCol;
	private JButton pantryAddBtn;
	private JScrollPane scrollPane_5;
	private JTextArea instruction_input;
	private JButton btnNewButton_3;
	private JScrollPane scrollPane_6;
	private JTextArea shopList;
	private JTextArea shopListConfirm;

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
					.addContainerGap(161, Short.MAX_VALUE))
		);
		gl_profile.setVerticalGroup(
			gl_profile.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_profile.createSequentialGroup()
					.addGap(273)
					.addComponent(btnNewButton, GroupLayout.PREFERRED_SIZE, 54, GroupLayout.PREFERRED_SIZE)
					.addGap(27)
					.addComponent(btnNewButton_1, GroupLayout.PREFERRED_SIZE, 54, GroupLayout.PREFERRED_SIZE)
					.addContainerGap(283, Short.MAX_VALUE))
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

		JLabel lblMainPage = new JLabel("WELCOME TO MEALCRAFT!!!  ");
		lblMainPage.setFont(new Font("Comic Sans MS", Font.PLAIN, 27));
		
		JLabel lblPleaseSelectOne = new JLabel("Please select one of the tabs to your left  if you know what you are doing.\r\n");
		lblPleaseSelectOne.setFont(new Font("Comic Sans MS", Font.PLAIN, 14));
		
		JLabel lblIfYouDont = new JLabel("If you dont  know what you are doing visit our help page");
		lblIfYouDont.setFont(new Font("Comic Sans MS", Font.PLAIN, 16));
		GroupLayout gl_tabBodyMain = new GroupLayout(tabBodyMain);
		gl_tabBodyMain.setHorizontalGroup(
			gl_tabBodyMain.createParallelGroup(Alignment.TRAILING)
				.addGroup(gl_tabBodyMain.createSequentialGroup()
					.addGap(306)
					.addComponent(lblMainPage)
					.addContainerGap(136, Short.MAX_VALUE))
				.addGroup(gl_tabBodyMain.createSequentialGroup()
					.addContainerGap(257, Short.MAX_VALUE)
					.addComponent(lblPleaseSelectOne, GroupLayout.PREFERRED_SIZE, 598, GroupLayout.PREFERRED_SIZE)
					.addGap(149))
				.addGroup(gl_tabBodyMain.createSequentialGroup()
					.addContainerGap(305, Short.MAX_VALUE)
					.addComponent(lblIfYouDont)
					.addGap(289))
		);
		gl_tabBodyMain.setVerticalGroup(
			gl_tabBodyMain.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_tabBodyMain.createSequentialGroup()
					.addGap(20)
					.addComponent(lblMainPage, GroupLayout.PREFERRED_SIZE, 94, GroupLayout.PREFERRED_SIZE)
					.addGap(116)
					.addComponent(lblPleaseSelectOne, GroupLayout.PREFERRED_SIZE, 133, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED, 183, Short.MAX_VALUE)
					.addComponent(lblIfYouDont)
					.addGap(27))
		);
		tabBodyMain.setLayout(gl_tabBodyMain);

		JPanel tabBodyPantry = new JPanel();
		TAB_BODY.add(tabBodyPantry, "tabBodyPantry");
		redoPTable();

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
		Document textFieldDocB = txtEgBacon.getDocument();
		textFieldDocB.addDocumentListener(new DocumentListener() {
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
				try{
					if(txtEgBacon.getText().charAt(0) != ' '){
						try{
							pantryAddBtn.setEnabled(enable);
						}catch(Exception ex){}
					}
				}catch(Exception ext){}
				
				try{
					if(txtEgBacon.getText().charAt(0) == ' '){
						try{
							pantryAddBtn.setEnabled(false);
						}catch(Exception ex){}
					}
				}catch(Exception ext){}
			}
		});
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

		pantryAddBtn = new JButton("Add");
		pantryAddBtn.setEnabled(false);
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

		tabBodyRecipe = new JPanel();
		TAB_BODY.add(tabBodyRecipe, "tabBodyRecipe");

		JLabel lblRecipes = new JLabel("Recipes:");
		lblRecipes.setFont(new Font("Comic Sans MS", Font.PLAIN, 17));


		scrollPane_1 = new JScrollPane();
		redoRList();

		recipeSwitch = new JPanel();

		JButton addRcp = new JButton("+");
		addRcp.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				//THIS IS JASONS SPOT, IT IS A GOOD SPOT
				CardLayout toRecipeEdit = (CardLayout)(recipeSwitch.getLayout());
				toRecipeEdit.show(recipeSwitch, "recipeNameInput");
			}
		});
		addRcp.setPreferredSize(new Dimension(37, 23));
		addRcp.setFont(new Font("Comic Sans MS", Font.BOLD, 13));

		removeRcp = new JButton("-");
		removeRcp.setEnabled(false);
		removeRcp.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					RecipeToIngredientsDb recipe_relation = new RecipeToIngredientsDb();
					RecipeBookDb recipebook = new RecipeBookDb();

					recipe_relation.removeKey("recipe_id", "recipe_to_ingredients", ""+recipebook.getId((String)listORecipe.getSelectedValue()));
					recipebook.removeKey("name", "recipebook", (String)listORecipe.getSelectedValue());
				} catch (Exception e1) {
					e1.printStackTrace();
				}
				redoRList();
				removeRcp.setEnabled(false);
				if (listORecipe.getSelectedValue()==null){
					redoRecipeIngrTable();
					System.out.println("worked");
				}
			}
		});
		removeRcp.setPreferredSize(new Dimension(37, 23));
		removeRcp.setFont(new Font("Comic Sans MS", Font.BOLD, 13));

		GroupLayout gl_tabBodyRecipe = new GroupLayout(tabBodyRecipe);
		gl_tabBodyRecipe.setHorizontalGroup(
			gl_tabBodyRecipe.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_tabBodyRecipe.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_tabBodyRecipe.createParallelGroup(Alignment.TRAILING)
						.addGroup(gl_tabBodyRecipe.createSequentialGroup()
							.addGroup(gl_tabBodyRecipe.createParallelGroup(Alignment.LEADING)
								.addComponent(lblRecipes, GroupLayout.PREFERRED_SIZE, 158, GroupLayout.PREFERRED_SIZE)
								.addComponent(scrollPane_1, GroupLayout.PREFERRED_SIZE, 267, GroupLayout.PREFERRED_SIZE))
							.addPreferredGap(ComponentPlacement.RELATED))
						.addGroup(gl_tabBodyRecipe.createSequentialGroup()
							.addComponent(addRcp, GroupLayout.PREFERRED_SIZE, 95, GroupLayout.PREFERRED_SIZE)
							.addGap(27)
							.addComponent(removeRcp, GroupLayout.PREFERRED_SIZE, 95, GroupLayout.PREFERRED_SIZE)
							.addGap(33)))
					.addComponent(recipeSwitch, GroupLayout.PREFERRED_SIZE, 711, GroupLayout.PREFERRED_SIZE)
					.addContainerGap(10, Short.MAX_VALUE))
		);
		gl_tabBodyRecipe.setVerticalGroup(
			gl_tabBodyRecipe.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_tabBodyRecipe.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_tabBodyRecipe.createParallelGroup(Alignment.LEADING)
						.addComponent(recipeSwitch, GroupLayout.PREFERRED_SIZE, 575, GroupLayout.PREFERRED_SIZE)
						.addGroup(gl_tabBodyRecipe.createSequentialGroup()
							.addComponent(lblRecipes, GroupLayout.PREFERRED_SIZE, 43, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(scrollPane_1, GroupLayout.PREFERRED_SIZE, 470, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addGroup(gl_tabBodyRecipe.createParallelGroup(Alignment.BASELINE)
								.addComponent(removeRcp, GroupLayout.PREFERRED_SIZE, 38, GroupLayout.PREFERRED_SIZE)
								.addComponent(addRcp, GroupLayout.PREFERRED_SIZE, 38, GroupLayout.PREFERRED_SIZE))))
					.addContainerGap())
		);
		recipeSwitch.setLayout(new CardLayout(0, 0));

		recipeView = new JPanel();
		recipeSwitch.add(recipeView, "recipeView");
		recipeView.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));

		JLabel ingr_lbl = new JLabel("Ingredients:");
		ingr_lbl.setFont(new Font("Comic Sans MS", Font.PLAIN, 17));

		scrollPane_2 = new JScrollPane();

		scrollPane_3 = new JScrollPane();
		GroupLayout gl_recipeView = new GroupLayout(recipeView);
		gl_recipeView.setHorizontalGroup(
				gl_recipeView.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_recipeView.createSequentialGroup()
						.addGroup(gl_recipeView.createParallelGroup(Alignment.LEADING)
								.addComponent(ingr_lbl, GroupLayout.PREFERRED_SIZE, 158, GroupLayout.PREFERRED_SIZE)
								.addGroup(gl_recipeView.createSequentialGroup()
										.addContainerGap()
										.addComponent(scrollPane_2, GroupLayout.PREFERRED_SIZE, 684, GroupLayout.PREFERRED_SIZE))
										.addGroup(gl_recipeView.createSequentialGroup()
												.addContainerGap()
												.addComponent(scrollPane_3, GroupLayout.PREFERRED_SIZE, 684, GroupLayout.PREFERRED_SIZE)))
												.addContainerGap(13, Short.MAX_VALUE))
				);
		gl_recipeView.setVerticalGroup(
				gl_recipeView.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_recipeView.createSequentialGroup()
						.addComponent(ingr_lbl, GroupLayout.PREFERRED_SIZE, 43, GroupLayout.PREFERRED_SIZE)
						.addPreferredGap(ComponentPlacement.RELATED)
						.addComponent(scrollPane_2, GroupLayout.PREFERRED_SIZE, 224, GroupLayout.PREFERRED_SIZE)
						.addGap(18)
						.addComponent(scrollPane_3, GroupLayout.PREFERRED_SIZE, 269, GroupLayout.PREFERRED_SIZE)
						.addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
				);
		recipeView.setLayout(gl_recipeView);

		JPanel recipeEdit = new JPanel();
		recipeEdit.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		recipeSwitch.add(recipeEdit, "recipeEdit");
		
		JLabel lblNewLabel_2 = new JLabel("Recipe Name:");
		lblNewLabel_2.setFont(new Font("Comic Sans MS", Font.BOLD, 13));
		
		JLabel lblNewLabel_3 = new JLabel("Ingredients: Name, quantity, price");
		lblNewLabel_3.setFont(new Font("Comic Sans MS", Font.BOLD, 12));
		
		scrollPane_4 = new JScrollPane();
		
		col_ingr_input = new JTextField();
		col_ingr_input.setColumns(10);
		
		Document textFieldDocCol = col_ingr_input.getDocument();
		textFieldDocCol.addDocumentListener(new DocumentListener() {
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
				addToCol.setEnabled(enable);
			}
		});
		
		col_quan_input = new JTextField();
		col_quan_input.setColumns(10);
		
		comboBox_2 = new JComboBox(units);
		
		col_calo_input = new JTextField();
		col_calo_input.setColumns(10);
		
		addToCol = new JButton("Add");
		addToCol.setEnabled(false);
		addToCol.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				col_ingr.add(col_ingr_input.getText());
				col_quan.add(col_quan_input.getText());
				col_unit.add((String) comboBox_2.getSelectedItem());
				col_calo.add(col_calo_input.getText());
				
				redoAddRecipeIngrTable((String)listORecipe.getSelectedValue());
			
			}
		});
		
		btnNewButton_3 = new JButton("DONE");
		btnNewButton_3.setEnabled(false);
		btnNewButton_3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
			
				
				
				try {
					IngredientDb ingr = new IngredientDb();
					RecipeToIngredientsDb rti = new RecipeToIngredientsDb();
					RecipeBookDb rb = new RecipeBookDb();
					rb.removeKey("name", "recipebook", newRecipe);
					Recipe recipe = new Recipe(newRecipe, instruction_input.getText(), "");
					rb.addRecipe(recipe);
		
					
			
				int i = 0;
				for (String each: col_ingr){
					if (each!=null){
					Ingredient ingredient = new Ingredient(each, Double.parseDouble(col_calo.get(i)));
					ingr.addIngredient(ingredient, new Quantity(0, "lbs"));
					rti.addRelation(newRecipe, each, Double.parseDouble(col_quan.get(i)), ""+col_unit.get(i));
					}
					i++;
				}
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				redoPTable();
				pantryTable.revalidate();
				pantryTable.repaint();
				scrollPane.setViewportView(pantryTable);
				scrollPane.revalidate();
				scrollPane.repaint();
				
				CardLayout toRecipeview = (CardLayout)(recipeSwitch.getLayout());
				toRecipeview.show(recipeSwitch, "recipeView");
				
				instruction_input.setText("Input steps to make this Recipe Here!");
				redoAddRecipeIngrTable();
				
			}
		});
		
		JLabel lblNewLabel_4 = new JLabel("Instructions:");
		lblNewLabel_4.setFont(new Font("Comic Sans MS", Font.BOLD, 12));
		
		scrollPane_5 = new JScrollPane();
		instruction_input = new JTextArea("Please input the steps for this recipe here");
		
		Document textFieldDocII = instruction_input.getDocument();
		textFieldDocII.addDocumentListener(new DocumentListener() {
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
				btnNewButton_3.setEnabled(enable);
			}
		});
		
		
		
		scrollPane_5.setViewportView(instruction_input);
		
		lblNewLabel_6 = new JLabel("");
		lblNewLabel_6.setFont(new Font("Comic Sans MS", Font.PLAIN, 12));
		
		JButton btnNewButton_2 = new JButton("resetList");
		btnNewButton_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				col_ingr = new ArrayList<String>();
				col_quan = new ArrayList<String>();
				col_unit = new ArrayList<String>();
				col_calo = new ArrayList<String>();
				
				redoAddRecipeIngrTable();
				
				
			}
		});
		GroupLayout gl_recipeEdit = new GroupLayout(recipeEdit);
		gl_recipeEdit.setHorizontalGroup(
			gl_recipeEdit.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_recipeEdit.createSequentialGroup()
					.addGap(20)
					.addGroup(gl_recipeEdit.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_recipeEdit.createSequentialGroup()
							.addComponent(scrollPane_5, GroupLayout.PREFERRED_SIZE, 661, GroupLayout.PREFERRED_SIZE)
							.addContainerGap())
						.addGroup(gl_recipeEdit.createParallelGroup(Alignment.LEADING)
							.addGroup(gl_recipeEdit.createSequentialGroup()
								.addComponent(lblNewLabel_4, GroupLayout.PREFERRED_SIZE, 98, GroupLayout.PREFERRED_SIZE)
								.addContainerGap())
							.addGroup(gl_recipeEdit.createSequentialGroup()
								.addGroup(gl_recipeEdit.createParallelGroup(Alignment.TRAILING)
									.addGroup(gl_recipeEdit.createSequentialGroup()
										.addGroup(gl_recipeEdit.createParallelGroup(Alignment.LEADING)
											.addComponent(lblNewLabel_3, GroupLayout.DEFAULT_SIZE, 286, Short.MAX_VALUE)
											.addGroup(gl_recipeEdit.createSequentialGroup()
												.addComponent(lblNewLabel_2)
												.addPreferredGap(ComponentPlacement.UNRELATED)
												.addComponent(lblNewLabel_6, GroupLayout.DEFAULT_SIZE, 189, Short.MAX_VALUE)))
										.addGap(377))
									.addGroup(gl_recipeEdit.createSequentialGroup()
										.addGroup(gl_recipeEdit.createParallelGroup(Alignment.TRAILING)
											.addComponent(btnNewButton_3, GroupLayout.PREFERRED_SIZE, 106, GroupLayout.PREFERRED_SIZE)
											.addComponent(scrollPane_4, GroupLayout.PREFERRED_SIZE, 663, GroupLayout.PREFERRED_SIZE))
										.addPreferredGap(ComponentPlacement.RELATED))
									.addGroup(gl_recipeEdit.createSequentialGroup()
										.addComponent(col_ingr_input, GroupLayout.PREFERRED_SIZE, 125, GroupLayout.PREFERRED_SIZE)
										.addPreferredGap(ComponentPlacement.UNRELATED)
										.addComponent(col_quan_input, GroupLayout.PREFERRED_SIZE, 125, GroupLayout.PREFERRED_SIZE)
										.addGap(10)
										.addComponent(comboBox_2, 0, 106, Short.MAX_VALUE)
										.addPreferredGap(ComponentPlacement.UNRELATED)
										.addComponent(col_calo_input, GroupLayout.PREFERRED_SIZE, 125, GroupLayout.PREFERRED_SIZE)
										.addGap(18)
										.addGroup(gl_recipeEdit.createParallelGroup(Alignment.LEADING)
											.addComponent(btnNewButton_2, GroupLayout.PREFERRED_SIZE, 126, GroupLayout.PREFERRED_SIZE)
											.addComponent(addToCol, GroupLayout.PREFERRED_SIZE, 129, GroupLayout.PREFERRED_SIZE))
										.addGap(5)))
								.addGap(80)))))
		);
		gl_recipeEdit.setVerticalGroup(
			gl_recipeEdit.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_recipeEdit.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_recipeEdit.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblNewLabel_2)
						.addComponent(lblNewLabel_6, GroupLayout.PREFERRED_SIZE, 18, GroupLayout.PREFERRED_SIZE))
					.addGap(18)
					.addComponent(lblNewLabel_3, GroupLayout.PREFERRED_SIZE, 23, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(scrollPane_4, GroupLayout.PREFERRED_SIZE, 146, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(gl_recipeEdit.createParallelGroup(Alignment.BASELINE)
						.addComponent(col_ingr_input, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(col_calo_input, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(comboBox_2, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(col_quan_input, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(addToCol))
					.addGroup(gl_recipeEdit.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_recipeEdit.createSequentialGroup()
							.addGap(30)
							.addComponent(lblNewLabel_4, GroupLayout.PREFERRED_SIZE, 22, GroupLayout.PREFERRED_SIZE))
						.addGroup(gl_recipeEdit.createSequentialGroup()
							.addPreferredGap(ComponentPlacement.UNRELATED)
							.addComponent(btnNewButton_2, GroupLayout.PREFERRED_SIZE, 22, GroupLayout.PREFERRED_SIZE)))
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addComponent(scrollPane_5, GroupLayout.PREFERRED_SIZE, 187, GroupLayout.PREFERRED_SIZE)
					.addGap(7)
					.addComponent(btnNewButton_3, GroupLayout.PREFERRED_SIZE, 40, GroupLayout.PREFERRED_SIZE)
					.addContainerGap())
		);
		recipeEdit.setLayout(gl_recipeEdit);
		
		JPanel recipeNameInput = new JPanel();
		recipeNameInput.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		recipeSwitch.add(recipeNameInput, "recipeNameInput");
		
		JLabel lblNewLabel_5 = new JLabel("Please Enter New Recipe Name:");
		lblNewLabel_5.setFont(new Font("Comic Sans MS", Font.BOLD, 15));
		
		newRcpName = new JTextField();
		newRcpName.setFont(new Font("Comic Sans MS", Font.PLAIN, 14));
		newRcpName.setColumns(10);
		
		Document textFieldDoc_RcpName = newRcpName.getDocument();
		textFieldDoc_RcpName.addDocumentListener(new DocumentListener() {
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
				makeRcp.setEnabled(enable);
			}
		});
		
		makeRcp = new JButton("Next");
		makeRcp.setEnabled(false);
		makeRcp.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				newRecipe = newRcpName.getText();
				CardLayout toRecipeEdit = (CardLayout)(recipeSwitch.getLayout());
				toRecipeEdit.show(recipeSwitch, "recipeEdit");
				lblNewLabel_6.setText(newRecipe);
				//CODES GOES HERE
				try {
					RecipeBookDb recipeBook = new RecipeBookDb();
					Recipe recipe = new Recipe(newRcpName.getText(),"");
					recipeBook.addRecipe(recipe);
					recipeBook.close();
					redoRList();
				} catch (Exception e1) {
					e1.printStackTrace();
				}
				
				col_ingr = new ArrayList<String>();
				col_quan = new ArrayList<String>();
				col_unit = new ArrayList<String>();
				col_calo = new ArrayList<String>();
				
				newRcpName.setText(null);
			}
		});
		makeRcp.setFont(new Font("Comic Sans MS", Font.PLAIN, 12));
		GroupLayout gl_recipeNameInput = new GroupLayout(recipeNameInput);
		gl_recipeNameInput.setHorizontalGroup(
			gl_recipeNameInput.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_recipeNameInput.createSequentialGroup()
					.addContainerGap(217, Short.MAX_VALUE)
					.addGroup(gl_recipeNameInput.createParallelGroup(Alignment.TRAILING)
						.addGroup(gl_recipeNameInput.createSequentialGroup()
							.addGroup(gl_recipeNameInput.createParallelGroup(Alignment.TRAILING)
								.addComponent(newRcpName, GroupLayout.PREFERRED_SIZE, 276, GroupLayout.PREFERRED_SIZE)
								.addGroup(gl_recipeNameInput.createSequentialGroup()
									.addComponent(lblNewLabel_5)
									.addGap(21)))
							.addGap(214))
						.addGroup(gl_recipeNameInput.createSequentialGroup()
							.addComponent(makeRcp, GroupLayout.PREFERRED_SIZE, 102, GroupLayout.PREFERRED_SIZE)
							.addGap(301))))
		);
		gl_recipeNameInput.setVerticalGroup(
			gl_recipeNameInput.createParallelGroup(Alignment.TRAILING)
				.addGroup(gl_recipeNameInput.createSequentialGroup()
					.addContainerGap(212, Short.MAX_VALUE)
					.addComponent(lblNewLabel_5, GroupLayout.PREFERRED_SIZE, 40, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(newRcpName, GroupLayout.PREFERRED_SIZE, 33, GroupLayout.PREFERRED_SIZE)
					.addGap(18)
					.addComponent(makeRcp, GroupLayout.PREFERRED_SIZE, 54, GroupLayout.PREFERRED_SIZE)
					.addGap(208))
		);
		recipeNameInput.setLayout(gl_recipeNameInput);

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
		
		JLabel lblNewLabel_7 = new JLabel("HOW TO USE:");
		lblNewLabel_7.setFont(new Font("Comic Sans MS", Font.BOLD, 19));
		
		JTabbedPane helpTab = new JTabbedPane(JTabbedPane.TOP);
		
		
		GroupLayout gl_tabBodyTools = new GroupLayout(tabBodyTools);
		gl_tabBodyTools.setHorizontalGroup(
			gl_tabBodyTools.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_tabBodyTools.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_tabBodyTools.createParallelGroup(Alignment.LEADING)
						.addComponent(lblNewLabel_7, GroupLayout.PREFERRED_SIZE, 982, GroupLayout.PREFERRED_SIZE)
						.addComponent(helpTab, GroupLayout.PREFERRED_SIZE, 984, GroupLayout.PREFERRED_SIZE))
					.addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
		);
		gl_tabBodyTools.setVerticalGroup(
			gl_tabBodyTools.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_tabBodyTools.createSequentialGroup()
					.addContainerGap()
					.addComponent(lblNewLabel_7, GroupLayout.PREFERRED_SIZE, 59, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(helpTab, GroupLayout.PREFERRED_SIZE, 512, GroupLayout.PREFERRED_SIZE)
					.addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
		);
		
		JTextArea txtrClickThePantry = new JTextArea();
		txtrClickThePantry.setText("click the Pantry button on the upper left tab.\r\n\r\nTo add an ingredient:\r\ntype in your ingredients name, quantity, calories, and price in the forms on the bottom of the page. Press Add button.\r\n\r\nTo remove an ingredient:\r\nType in its name in the form at the bottom. Press  the Remove button.");
		helpTab.addTab("PANTRY", null, txtrClickThePantry, null);
		
		JTextArea textClickRecipe = new JTextArea();
		textClickRecipe.setText("Click on Recipe button on the upper left of the screen\r\n\r\nTo add recipes:\r\n1)click on the plus button\r\n2)enter recipename in the form\r\n3)Type in name, quanity, units, and price as you would for add ingredient.\r\n4)Click on add ingredient\r\n5)click on the instructions form, and type in your instructions\r\n6)Hit done\r\n\r\nTo remove:\r\nSelect the recipe that you want to remove\r\nHit minus");
		helpTab.addTab("RECIPE", null, textClickRecipe, null);
		
		JTextArea CalendarHelp = new JTextArea();
		CalendarHelp.setText("Click on calendar button on the middle of the left plane\r\n\r\nJust a simple calendar that displays dates and help you plan meals.");
		helpTab.addTab("CALENDAR", null, CalendarHelp, null);
		
		JTextArea ShoppingHelp = new JTextArea();
		ShoppingHelp.setText("Click on shoping list at the bottom of the left pane\r\n\r\nTo export:\r\ntype in your shopping list\r\nhit export\r\n\r\nTo view Shopping list:\r\nGo into your MealCraft directory, and open shoppinglist.txt with notepad\r\n");
		helpTab.addTab("SHOPPING LIST", null, ShoppingHelp, null);
		tabBodyTools.setLayout(gl_tabBodyTools);

		JPanel tabBodyShoppingList = new JPanel();
		TAB_BODY.add(tabBodyShoppingList, "tabBodyShoppingList");
		
		scrollPane_6 = new JScrollPane();
		shopList = new JTextArea("Please put your shopping List here!");
		
		scrollPane_6.setViewportView(shopList);
		
		JButton exportBtn = new JButton("Export to File!");
		exportBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				ShoppingList.exportToFile(shopList.getText());
				shopListConfirm.setText("shoppinglist.txt has been successfully created in local directory.");
			}
		});
		
		shopListConfirm = new JTextArea();
		
		GroupLayout gl_tabBodyShoppingList = new GroupLayout(tabBodyShoppingList);
		gl_tabBodyShoppingList.setHorizontalGroup(
			gl_tabBodyShoppingList.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_tabBodyShoppingList.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_tabBodyShoppingList.createParallelGroup(Alignment.TRAILING, false)
						.addGroup(gl_tabBodyShoppingList.createSequentialGroup()
							.addComponent(shopListConfirm, GroupLayout.PREFERRED_SIZE, 738, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.RELATED, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
							.addComponent(exportBtn, GroupLayout.PREFERRED_SIZE, 234, GroupLayout.PREFERRED_SIZE))
						.addComponent(scrollPane_6, GroupLayout.PREFERRED_SIZE, 980, GroupLayout.PREFERRED_SIZE))
					.addContainerGap(14, Short.MAX_VALUE))
		);
		gl_tabBodyShoppingList.setVerticalGroup(
			gl_tabBodyShoppingList.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_tabBodyShoppingList.createSequentialGroup()
					.addContainerGap()
					.addComponent(scrollPane_6, GroupLayout.PREFERRED_SIZE, 425, GroupLayout.PREFERRED_SIZE)
					.addGap(18)
					.addGroup(gl_tabBodyShoppingList.createParallelGroup(Alignment.LEADING)
						.addComponent(exportBtn, GroupLayout.PREFERRED_SIZE, 68, GroupLayout.PREFERRED_SIZE)
						.addComponent(shopListConfirm, GroupLayout.PREFERRED_SIZE, 66, GroupLayout.PREFERRED_SIZE))
					.addContainerGap(74, Short.MAX_VALUE))
		);
		tabBodyShoppingList.setLayout(gl_tabBodyShoppingList);

		JButton logout = new JButton("Log out/Switch");
		logout.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				CardLayout Plogout = (CardLayout)(TAB_BODY.getLayout());
				Plogout.show(TAB_BODY, "tabBodyMain");
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

		JButton tabTools = new JButton("Help");
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
		GroupLayout gl_tabSelection = new GroupLayout(tabSelection);
		gl_tabSelection.setHorizontalGroup(
			gl_tabSelection.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_tabSelection.createSequentialGroup()
					.addGap(24)
					.addGroup(gl_tabSelection.createParallelGroup(Alignment.LEADING)
						.addComponent(tabTools, GroupLayout.PREFERRED_SIZE, 129, GroupLayout.PREFERRED_SIZE)
						.addComponent(tabShoppingList, GroupLayout.PREFERRED_SIZE, 129, GroupLayout.PREFERRED_SIZE)
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
					.addComponent(tabShoppingList, GroupLayout.PREFERRED_SIZE, 45, GroupLayout.PREFERRED_SIZE)
					.addGap(46)
					.addComponent(tabTools, GroupLayout.PREFERRED_SIZE, 45, GroupLayout.PREFERRED_SIZE)
					.addContainerGap(146, Short.MAX_VALUE))
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
		pantryTable.setShowGrid(false);

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
				pantryTable.setShowGrid(false);

			}	
			myIngredientDb.close();
		}
		catch(Exception ect){

		}
		scrollPane.setViewportView(pantryTable);
	}
	public void redoRList(){
		try{
			RecipeBookDb recipeDb = new RecipeBookDb();
			rList = recipeDb.catArray("name");
			listORecipe = new JList(rList);
			listORecipe.setSelectionMode(1);
			listORecipe.addListSelectionListener(new ListSelectionListener() {
				public void valueChanged(ListSelectionEvent arg0) {
					if(listORecipe.getSelectedValue() != null){
						removeRcp.setEnabled(true);
					}
					if(listORecipe.getSelectedValue() == null){
						removeRcp.setEnabled(false);
					}
					CardLayout toRecipeView = (CardLayout)(recipeSwitch.getLayout());
					toRecipeView.show(recipeSwitch, "recipeView");

					redoRecipeIngrTable((String)listORecipe.getSelectedValue());
					System.out.println(listORecipe.getSelectedValue());
					currRcpName = (String) listORecipe.getSelectedValue();
					lblNewLabel_6.setText(currRcpName);
					
					Thread.currentThread();
					try {
						Thread.sleep(0);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			});
			
			recipeDb.close();
		}
		
		catch(Exception e){
			e.printStackTrace();
		}
		scrollPane_1.setViewportView(listORecipe);
	}
	public void redoRecipeIngrTable(String recipeSelected){
		recipeIngr = new JTable(50,3);
		recipeIngr.setEnabled(false);
		recipeIngr.setShowGrid(false);

		recipeInstructions = new JTextArea("stuff");
		recipeInstructions.setEnabled(true);

		try{
			RecipeToIngredientsDb recipe_relation = new RecipeToIngredientsDb();
			
			//IngredientDb r = new IngredientDb();
			String [] ingredients = recipe_relation.getIngredientsThatRequire(recipeSelected);
			
			//ArrayList<String> aUnits= new ArrayList<String>();
			int i =0;
			for(String each: ingredients){
				if (each!= null){

					ingr = new IngredientDb();
					RecipeBookDb recipebookDb = new RecipeBookDb();
					recipeIngr.setValueAt(each, i,0);
					recipeIngr.setValueAt(recipe_relation.getQuantity(recipeSelected, each)+" "+recipe_relation.getUnits(recipeSelected, each), i, 1);
					//recipeIngr.setValueAt(ingr.getCalories(recipeSelected),i,2);
					recipeIngr.setValueAt("$"+ingr.getPrice(each), i, 2);
					recipeIngr.setShowGrid(false);

					recipeInstructions = new JTextArea(recipebookDb.getSteps(recipeSelected));
					recipeInstructions.setEnabled(true);
					recipeInstructions.setEditable(false);

					i++;
					recipebookDb.close();

				}

			}

			recipe_relation.close();
			

		}
		catch(Exception e){
			e.printStackTrace();
		}
		
		scrollPane_3.setViewportView(recipeInstructions);
		scrollPane_2.setViewportView(recipeIngr);
	}
	public void redoRecipeIngrTable(){
		recipeIngr = new JTable(50,3);
		recipeIngr.setEnabled(false);
		recipeInstructions = new JTextArea("stuff");
		recipeInstructions.setEnabled(true);

		try{
			
		
			
				

			
					recipeIngr.setValueAt("", 0,0);
					recipeIngr.setValueAt("", 0, 1);
					recipeIngr.setValueAt("", 0, 2);
					recipeInstructions = new JTextArea("");
					recipeInstructions.setEnabled(true);
					recipeIngr.setShowGrid(false);



				

			

		}
		catch(Exception e){
			e.printStackTrace();
		}

		scrollPane_3.setViewportView(recipeInstructions);
		scrollPane_2.setViewportView(recipeIngr);
	}
	
	public void redoAddRecipeIngrTable(String recipeSelected){
		aRecipeIngr = new JTable(50,3);
		aRecipeIngr.setEnabled(false);
		aRecipeIngr.setShowGrid(false);


		try{
			
			int i =0;
			for(String aIngr: col_ingr){
				
					aRecipeIngr.setValueAt(aIngr, i,0);
					aRecipeIngr.setValueAt(col_quan.get(i) + " " + col_unit.get(i) ,i, 1);
					aRecipeIngr.setValueAt("$ " + col_calo.get(i) , i, 2);
					aRecipeIngr.setShowGrid(false);

					i++;

			}
			
		}
		catch(Exception e){
			e.printStackTrace();
		}
		
		scrollPane_4.setViewportView(aRecipeIngr);
	}
	public void redoAddRecipeIngrTable(){
		aRecipeIngr = new JTable(50,3);
		aRecipeIngr.setEnabled(false);
		aRecipeIngr.setShowGrid(false);


		try{
			
			int i =0;
			for(String aIngr: col_ingr){
				
				aRecipeIngr.setValueAt("", 0,0);
				aRecipeIngr.setValueAt("", 0, 1);
				aRecipeIngr.setValueAt("", 0, 2);
					aRecipeIngr.setShowGrid(false);

					i++;

			}
			
		}
		catch(Exception e){
			e.printStackTrace();
		}
		
		scrollPane_4.setViewportView(aRecipeIngr);
	}
}

