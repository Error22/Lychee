package com.error22.lychee.editor.gui;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.LayoutStyle.ComponentPlacement;

import com.error22.lychee.editor.LycheeEditor;
import com.error22.lychee.editor.network.ClientNetworkHandler;
import com.error22.lychee.network.NetworkExtension;

public class ConnectDialog extends JFrame {
	private static final long serialVersionUID = -7433439230716128721L;
	private JTextField tfServerAddress;
	private JTextField tfUsername;
	private JPasswordField tfPassword;

	public ConnectDialog() {
		setMinimumSize(new Dimension(460, 230));
		setTitle("Connect to server");
		setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		setBounds(100, 100, 460, 230);

		JLabel lblNewLabel = new JLabel("Please enter the address of the Lychee server you would like to connect to.");

		JLabel lblServerAddress = new JLabel("Server Address");

		tfServerAddress = new JTextField();
		tfServerAddress.setColumns(10);

		JLabel lblIfYouWould = new JLabel("If you would like to authenticate please enter the username and password");

		JLabel lblBelowHoweverIt = new JLabel("below, however it is not necessary for all servers.");

		JLabel lblUsername = new JLabel("Username");

		tfUsername = new JTextField();
		tfUsername.setColumns(10);

		JLabel lblPassword = new JLabel("Password");

		tfPassword = new JPasswordField();
		tfPassword.setColumns(10);

		JButton btnConnect = new JButton("Connect");

		JLabel lblStatus = new JLabel("Ready");

		btnConnect.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				tfServerAddress.setEnabled(false);
				tfUsername.setEnabled(false);
				tfPassword.setEnabled(false);
				btnConnect.setEnabled(false);

				String tempAddress = tfServerAddress.getText();
				int tempPort = 1234;

				if (tempAddress.contains(":")) {
					tempPort = Integer.parseInt(tempAddress.substring(tempAddress.indexOf(":")+1));
					tempAddress = tempAddress.substring(0, tempAddress.indexOf(":"));
				}

				lblStatus.setText("Connecting....");

				String address = tempAddress;
				int port = tempPort;
				String username = tfUsername.getText();
				String password = new String(tfPassword.getPassword());

				new Thread("Connector") {
					public void run() {
						try {
							LycheeEditor.INSTANCE.connectToServer(address, port);
							ClientNetworkHandler handler = LycheeEditor.INSTANCE.getNetworkHandler();

							lblStatus.setText("Initialising connection...");
							handler.awaitFullConnected();

							if (username != null && username.length() > 0) {
								lblStatus.setText("Authenticating...");
								if (!handler.getExtensions().isEnabled(NetworkExtension.Authentication)) {
									throw new Exception("Server does not support authentication");
								}
								// TODO: Add authentication
								throw new Exception("Authentication is not yet supported (editor-side)");
							}

							// TODO: Ensure server supports the needed
							// extensions for the editor to function, not all
							// extensions are required however but without a few
							// basic ones the client can not function at all and
							// there is no point in connecting.
							//Needed:
							//EXT_BASE, EXT_AUTHENTICATION, EXT_PAIRED_PACKETS, EXT_PROJECT_MANAGEMENT
							

							lblStatus.setText("Connected!");
							JOptionPane.showMessageDialog(ConnectDialog.this,
									"The connection to the Lychee server was made successfully!\nServer Version Identity: "
											+ handler.getServerIdent() + "\nExtensions: "
											+ handler.getExtensions().getInternalSet().size() + "/"
											+ ClientNetworkHandler.allSupportedExtensions.getInternalSet().size(),
									"Connected to server!", JOptionPane.INFORMATION_MESSAGE);
							ConnectDialog.this.dispose();
							LycheeEditor.INSTANCE.completeConnection();
							return;
						} catch (Exception e) {
							e.printStackTrace();
							lblStatus.setText("Failed to connect! \"" + e.getMessage() + "\"");
						}

						tfServerAddress.setEnabled(true);
						tfUsername.setEnabled(true);
						tfPassword.setEnabled(true);
						btnConnect.setEnabled(true);

					};
				}.start();
			}
		});

		GroupLayout groupLayout = new GroupLayout(getContentPane());
		groupLayout.setHorizontalGroup(groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup().addContainerGap()
						.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
								.addComponent(lblIfYouWould, GroupLayout.DEFAULT_SIZE, 424, Short.MAX_VALUE)
								.addComponent(lblNewLabel, GroupLayout.DEFAULT_SIZE, 424, Short.MAX_VALUE)
								.addGroup(groupLayout.createSequentialGroup().addComponent(lblServerAddress)
										.addPreferredGap(ComponentPlacement.RELATED)
										.addComponent(tfServerAddress, GroupLayout.DEFAULT_SIZE, 346, Short.MAX_VALUE))
						.addComponent(lblBelowHoweverIt, GroupLayout.DEFAULT_SIZE, 424, Short.MAX_VALUE)
						.addGroup(groupLayout.createSequentialGroup()
								.addGroup(groupLayout.createParallelGroup(Alignment.LEADING).addComponent(lblUsername)
										.addComponent(lblPassword))
								.addPreferredGap(ComponentPlacement.RELATED)
								.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
										.addComponent(tfPassword, GroupLayout.DEFAULT_SIZE, 372, Short.MAX_VALUE)
										.addComponent(tfUsername, GroupLayout.DEFAULT_SIZE, 372, Short.MAX_VALUE)))
						.addGroup(groupLayout.createSequentialGroup().addComponent(btnConnect)
								.addPreferredGap(ComponentPlacement.RELATED)
								.addComponent(lblStatus, GroupLayout.DEFAULT_SIZE, 347, Short.MAX_VALUE)))
				.addContainerGap()));
		groupLayout.setVerticalGroup(groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup().addContainerGap().addComponent(lblNewLabel)
						.addPreferredGap(ComponentPlacement.RELATED)
						.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE).addComponent(lblServerAddress)
								.addComponent(tfServerAddress, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
										GroupLayout.PREFERRED_SIZE))
						.addPreferredGap(ComponentPlacement.RELATED).addComponent(lblIfYouWould)
						.addPreferredGap(ComponentPlacement.RELATED).addComponent(lblBelowHoweverIt)
						.addPreferredGap(ComponentPlacement.RELATED)
						.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE).addComponent(lblUsername)
								.addComponent(tfUsername, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
										GroupLayout.PREFERRED_SIZE))
				.addPreferredGap(ComponentPlacement.RELATED)
				.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE).addComponent(lblPassword).addComponent(
						tfPassword, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
				.addPreferredGap(ComponentPlacement.RELATED, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
				.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE).addComponent(btnConnect)
						.addComponent(lblStatus)).addContainerGap()));
		getContentPane().setLayout(groupLayout);
	}

}
