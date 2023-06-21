package view;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Rectangle;
import java.awt.SystemColor;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.text.DateFormat;
import java.util.Date;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class Principal extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JLabel lblData;
	public JLabel lblUsuario;
	public JButton btnUsuarios;
	public JButton btnRelatorios;
	public JPanel panelRodape;
	public JButton btnMecanicos;
	public JButton btnProdutos;
	public JButton btnFornecedor;

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Principal frame = new Principal();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public Principal() {
		setResizable(false);
		setIconImage(Toolkit.getDefaultToolkit().getImage(Principal.class.getResource("/img/bicicleta.png")));
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowActivated(WindowEvent e) {
				setarData();

			}
		});
		setTitle("Home");
		setBackground(new Color(240, 240, 240));
		setBounds(new Rectangle(0, 0, 640, 480));
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 780, 417);
		contentPane = new JPanel();
		contentPane.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		contentPane.setToolTipText("relatorios");
		contentPane.setBackground(new Color(255, 255, 255));
		contentPane.setBorder(null);
		setLocationRelativeTo(null);

		setContentPane(contentPane);

		JButton btnSobre = new JButton("");
		btnSobre.setBorder(null);
		btnSobre.setContentAreaFilled(false);
		btnSobre.setBounds(690, 11, 64, 64);
		btnSobre.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Sobre sobre = new Sobre();
				sobre.setVisible(true);
			}
		});
		contentPane.setLayout(null);
		btnSobre.setIcon(new ImageIcon(Principal.class.getResource("/img/about.png")));
		contentPane.add(btnSobre);

		panelRodape = new JPanel();
		panelRodape.setBackground(SystemColor.desktop);
		panelRodape.setForeground(SystemColor.desktop);
		panelRodape.setBounds(0, 337, 764, 43);
		contentPane.add(panelRodape);
		panelRodape.setLayout(null);

		lblData = new JLabel("");
		lblData.setBounds(316, 0, 448, 43);
		panelRodape.add(lblData);
		lblData.setForeground(SystemColor.text);
		lblData.setFont(new Font("Tahoma", Font.PLAIN, 16));

		lblUsuario = new JLabel("");
		lblUsuario.setBounds(67, 0, 228, 43);
		panelRodape.add(lblUsuario);
		lblUsuario.setForeground(new Color(255, 255, 255));
		lblUsuario.setFont(new Font("Tahoma", Font.BOLD, 12));

		JLabel lblNewLabel_1 = new JLabel("Usuario");
		lblNewLabel_1.setForeground(new Color(255, 255, 255));
		lblNewLabel_1.setBounds(10, 0, 91, 43);
		panelRodape.add(lblNewLabel_1);
		lblNewLabel_1.setBackground(new Color(255, 255, 255));

		JLabel lblLogo = new JLabel("");
		lblLogo.setBorder(null);
		lblLogo.setIcon(new ImageIcon(Principal.class.getResource("/img/logo.png")));
		lblLogo.setBounds(508, 20, 256, 360);
		contentPane.add(lblLogo);

		btnUsuarios = new JButton("");
		btnUsuarios.setVisible(false);
		btnUsuarios.setEnabled(false);
		btnUsuarios.setContentAreaFilled(false);
		btnUsuarios.setBorderPainted(false);
		btnUsuarios.setBorder(null);
		btnUsuarios.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Usuarios usuarios = new Usuarios();
				usuarios.setVisible(true);
			}
		});
		btnUsuarios.setToolTipText("Usuarios");
		btnUsuarios.setIcon(new ImageIcon(Principal.class.getResource("/img/workers.png")));
		btnUsuarios.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnUsuarios.setBounds(286, 21, 128, 128);
		contentPane.add(btnUsuarios);

		JLabel lblNewLabel = new JLabel("Usuario :");
		lblNewLabel.setBounds(0, 337, 306, 43);
		contentPane.add(lblNewLabel);
		lblNewLabel.setForeground(new Color(255, 255, 255));
		lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 11));

		btnRelatorios = new JButton("");
		btnRelatorios.setVisible(false);
		btnRelatorios.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Relatorios relatorios = new Relatorios();
				relatorios.setVisible(true);
			}
		});
		btnRelatorios.setEnabled(false);
		btnRelatorios.setToolTipText("Relatorios");
		btnRelatorios.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnRelatorios.setContentAreaFilled(false);
		btnRelatorios.setBorder(null);
		btnRelatorios.setIcon(new ImageIcon(Principal.class.getResource("/img/graphic_icon.png")));
		btnRelatorios.setBorderPainted(false);
		btnRelatorios.setBounds(286, 160, 128, 128);
		contentPane.add(btnRelatorios);

		JButton btnClientes = new JButton("");
		btnClientes.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Clientes clientes = new Clientes();
				clientes.setVisible(true);
			}
		});
		btnClientes.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnClientes.setBorder(null);
		btnClientes.setContentAreaFilled(false);
		btnClientes.setIcon(new ImageIcon(Principal.class.getResource("/img/client.png")));
		btnClientes.setToolTipText("Clientes");
		btnClientes.setBounds(10, 11, 128, 128);
		contentPane.add(btnClientes);

		JButton btnSerivico = new JButton("");
		btnSerivico.addActionListener(new ActionListener() {
			@SuppressWarnings("unchecked")
			public void actionPerformed(ActionEvent e) {
				@SuppressWarnings("rawtypes")
				Servico servicos = new Servico();
				servicos.setVisible(true);
				servicos.usuario = lblUsuario.getText();
			}
		});
		btnSerivico.setIcon(new ImageIcon(
				Principal.class.getResource("/img/4620054_call_call center_phone_question_service_icon.png")));
		btnSerivico.setBorder(null);
		btnSerivico.setContentAreaFilled(false);
		btnSerivico.setToolTipText("Ordem de Servico");
		btnSerivico.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnSerivico.setBounds(10, 150, 128, 128);
		contentPane.add(btnSerivico);

		btnMecanicos = new JButton("");
		btnMecanicos.setVisible(false);
		btnMecanicos.setEnabled(false);
		btnMecanicos.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				mecanicos mecanicos = new mecanicos();
				mecanicos.setVisible(true);

			}
		});
		btnMecanicos.setToolTipText("Mecanicos");
		btnMecanicos.setContentAreaFilled(false);
		btnMecanicos.setBorder(null);
		btnMecanicos.setIcon(new ImageIcon(Principal.class.getResource("/img/mechanic.png")));
		btnMecanicos.setBounds(419, 11, 128, 128);
		contentPane.add(btnMecanicos);

		btnProdutos = new JButton("");
		btnProdutos.setToolTipText("produtos");
		btnProdutos.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				@SuppressWarnings("rawtypes")
				Produto produto = new Produto();
				produto.setVisible(true);
			}
		});
		btnProdutos.setVisible(false);
		btnProdutos.setEnabled(false);

		btnProdutos.setContentAreaFilled(false);
		btnProdutos.setBorder(null);
		btnProdutos.setIcon(
				new ImageIcon(Principal.class.getResource("/img/8333876_warehouse_storage unit_storehouse_icon.png")));
		btnProdutos.setBounds(148, 11, 128, 128);
		contentPane.add(btnProdutos);

		btnFornecedor = new JButton("");
		btnFornecedor.setToolTipText("fornecedores");
		btnFornecedor.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Fornecedor fornecedor = new Fornecedor();
				fornecedor.setVisible(true);

			}
		});
		btnFornecedor.setVisible(false);
		btnFornecedor.setEnabled(false);
		btnFornecedor.setContentAreaFilled(false);
		btnFornecedor.setBorder(null);
		btnFornecedor.setIcon(
				new ImageIcon(Principal.class.getResource("/img/3558102_food_noodles_shop_store_street_icon.png")));
		btnFornecedor.setBounds(148, 150, 128, 128);
		contentPane.add(btnFornecedor);
	}

	private void setarData() {
		Date data = new Date();
		DateFormat formatador = DateFormat.getDateInstance(DateFormat.FULL);
		lblData.setText(formatador.format(data));
	}
}