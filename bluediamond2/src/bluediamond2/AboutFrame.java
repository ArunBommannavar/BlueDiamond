package bluediamond2;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.Color;
import javax.swing.JButton;

public class AboutFrame extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private LogoPanel logoPanel = new LogoPanel();
	private JPanel infoPanel;
	private JLabel lblNewLabel;
	private JButton btnNewButton;
	
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					AboutFrame frame = new AboutFrame();
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
	public AboutFrame() {

		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 509, 436);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new BorderLayout(0, 0));
		
		infoPanel = new JPanel();
		infoPanel.setBackground(new Color(255, 255, 224));
		contentPane.add(infoPanel, BorderLayout.NORTH);
		infoPanel.setLayout(new BorderLayout(0, 0));
		
		lblNewLabel = new JLabel("<html>Bluediamond<br>Author: Arun Bommannavar<br>(Build Date: March 17,2022)</htlm>");
		lblNewLabel.setBackground(new Color(253, 245, 230));
		lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		infoPanel.add(lblNewLabel, BorderLayout.NORTH);
		
		contentPane.add(logoPanel, BorderLayout.CENTER);
		
		JPanel buttonPanel = new JPanel();
		buttonPanel.setBackground(new Color(211, 211, 211));
		contentPane.add(buttonPanel, BorderLayout.SOUTH);
		
		btnNewButton = new JButton("Close");
		btnNewButton.setFont(new Font("Tahoma", Font.PLAIN, 14));
		buttonPanel.add(btnNewButton);
		btnNewButton.addActionListener(new AboutFrame_exit_ActionAdapter(this));
	}
	
	
	public void closeAboutFrame(){
		this.dispose();
	}
}

class AboutFrame_exit_ActionAdapter implements ActionListener{
	private AboutFrame adaptee;
	
	AboutFrame_exit_ActionAdapter(AboutFrame adaptee){
		this.adaptee = adaptee;
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		adaptee.closeAboutFrame();
		
	}	
}
