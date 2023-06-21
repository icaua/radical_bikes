package view;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import model.DAO;
import util.Validador;

public class Login extends JFrame {
	DAO dao = new DAO();
	private Connection con;
	private PreparedStatement pst;
	private ResultSet rs;

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JLabel lblStatus;
	private JTextField txtLogin;
	private JPasswordField txtSenha;

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Login frame = new Login();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public Login() {
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowActivated(WindowEvent e) {
				status();
			}
		});
		setIconImage(Toolkit.getDefaultToolkit().getImage(Login.class.getResource("/img/radical bikes.png")));
		setTitle("Radical bikes - Login");
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setLocationRelativeTo(null);

		setContentPane(contentPane);
		contentPane.setLayout(null);

		JButton btnLogin = new JButton("Acessar");
		btnLogin.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Logar();
			}
		});
		btnLogin.setBounds(182, 131, 89, 23);
		contentPane.add(btnLogin);

		lblStatus = new JLabel("");
		lblStatus.setIcon(new ImageIcon(Login.class.getResource("/img/dboff.png")));
		lblStatus.setBounds(8, 145, 48, 48);
		contentPane.add(lblStatus);

		JLabel lblNewLabel = new JLabel("Login");
		lblNewLabel.setBounds(10, 27, 46, 17);
		contentPane.add(lblNewLabel);

		txtLogin = new JTextField();
		txtLogin.setBounds(66, 24, 205, 20);
		contentPane.add(txtLogin);
		txtLogin.setColumns(10);
		txtLogin.setDocument(new Validador(20));

		JLabel lblNewLabel_1 = new JLabel("Senha");
		lblNewLabel_1.setBounds(10, 58, 46, 17);
		contentPane.add(lblNewLabel_1);

		txtSenha = new JPasswordField();
		txtSenha.setBounds(66, 55, 205, 20);
		contentPane.add(txtSenha);
		txtSenha.setDocument(new Validador(250));

		getRootPane().setDefaultButton(btnLogin);

	}

	private void Logar() {
		String capturaSenha = new String(txtSenha.getPassword());

		if (txtLogin.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Informe o Login");
			txtLogin.requestFocus();
		} else if (capturaSenha.length() == 0) {
			JOptionPane.showMessageDialog(null, "Informe a senha");
			txtSenha.requestFocus();
		} else {
			String read = "select * from usuarios where login = ? and senha = md5(?)";
			try {
				con = dao.conectar();
				pst = con.prepareStatement(read);
				pst.setString(1, txtLogin.getText());
				pst.setString(2, capturaSenha);
				rs = pst.executeQuery();
				if (rs.next()) {
					String perfil = rs.getString(5);
					String cor = "#ed1b24";

					if (perfil.equals("admin")) {

						Principal principal = new Principal();
						principal.btnRelatorios.setEnabled(true);
						principal.btnRelatorios.setVisible(true);
						principal.btnUsuarios.setEnabled(true);
						principal.btnUsuarios.setVisible(true);
						principal.btnMecanicos.setEnabled(true);
						principal.btnMecanicos.setVisible(true);
						principal.btnProdutos.setVisible(true);
						principal.btnProdutos.setEnabled(true);
						principal.btnFornecedor.setVisible(true);
						principal.btnFornecedor.setEnabled(true);
						principal.panelRodape.setBackground(Color.decode(cor));
						principal.setVisible(true);
						principal.lblUsuario.setText(rs.getString(2));
						this.dispose();

					} else {

						Principal principal = new Principal();
						principal.setVisible(true);
						principal.lblUsuario.setText(rs.getString(2));
						this.dispose();
					}

				} else {
					JOptionPane.showMessageDialog(null, "usuario e/ou inexistente");
				}

				con.close();

			} catch (Exception e) {
				System.out.println(e);
			}

		}

	}

	private void status() {
		try {
			con = dao.conectar();
			if (con == null) {
				lblStatus.setIcon(new ImageIcon(Login.class.getResource("/img/dboff.png")));
			} else {
				lblStatus.setIcon(new ImageIcon(Login.class.getResource("/img/dbon.png")));
			}
			con.close();
		} catch (Exception e) {
			System.out.println(e);
		}
	}
}
