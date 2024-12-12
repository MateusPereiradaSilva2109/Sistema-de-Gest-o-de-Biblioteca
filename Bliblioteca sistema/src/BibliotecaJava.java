import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

public class BibliotecaJava {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new TelaLogin());
    }
}

// Classe Livro
class Livro {
    private String titulo;
    private String autor;
    private boolean emprestado;

    public Livro(String titulo, String autor) {
        this.titulo = titulo;
        this.autor = autor;
        this.emprestado = false;
    }

    public String getTitulo() {
        return titulo;
    }

    public String getAutor() {
        return autor;
    }

    public boolean isEmprestado() {
        return emprestado;
    }

    public void emprestar() {
        this.emprestado = true;
    }

    public void devolver() {
        this.emprestado = false;
    }

    @Override
    public String toString() {
        return titulo + " - " + autor + (emprestado ? " (Emprestado)" : " (Disponível)");
    }
}

// Classe TelaLogin
class TelaLogin extends JFrame {
    public TelaLogin() {
        setTitle("Tela de Login");
        setSize(300, 200);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(3, 2, 10, 10));

        JLabel userLabel = new JLabel("Usuário:");
        JTextField userField = new JTextField();
        JLabel passLabel = new JLabel("Senha:");
        JPasswordField passField = new JPasswordField();
        JButton loginButton = new JButton("Entrar");

        panel.add(userLabel);
        panel.add(userField);
        panel.add(passLabel);
        panel.add(passField);
        panel.add(new JLabel());
        panel.add(loginButton);

        loginButton.addActionListener(e -> {
            String username = userField.getText();
            String password = new String(passField.getPassword());

            if ("admin".equals(username) && "12345".equals(password)) {
                new TelaHome();
                dispose();
            } else {
                JOptionPane.showMessageDialog(this, "Usuário ou senha incorretos.");
            }
        });

        add(panel);
        setVisible(true);
    }
}

// Classe TelaHome
class TelaHome extends JFrame {
    public TelaHome() {
        setTitle("Tela Home");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(4, 1, 10, 10));

        JButton btnAdicionar = new JButton("Adicionar Livro");
        JButton btnListar = new JButton("Listar Livros");
        JButton btnEmprestar = new JButton("Emprestar Livro");
        JButton btnDevolver = new JButton("Devolver Livro");

        btnAdicionar.addActionListener(e -> new TelaAdicionarLivro());
        btnListar.addActionListener(e -> new TelaListarLivros());
        btnEmprestar.addActionListener(e -> new TelaEmprestarLivro());
        btnDevolver.addActionListener(e -> new TelaDevolverLivro());

        panel.add(btnAdicionar);
        panel.add(btnListar);
        panel.add(btnEmprestar);
        panel.add(btnDevolver);

        add(panel);
        setVisible(true);
    }
}

// Gerenciador de Biblioteca
class Biblioteca {
    private static final List<Livro> livros = new ArrayList<>();

    public static void adicionarLivro(String titulo, String autor) {
        livros.add(new Livro(titulo, autor));
    }

    public static List<Livro> getLivros() {
        return livros;
    }
}

// Tela Adicionar Livro
class TelaAdicionarLivro extends JFrame {
    public TelaAdicionarLivro() {
        setTitle("Adicionar Livro");
        setSize(300, 200);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel(new GridLayout(3, 2, 10, 10));

        JLabel lblTitulo = new JLabel("Título:");
        JTextField txtTitulo = new JTextField();
        JLabel lblAutor = new JLabel("Autor:");
        JTextField txtAutor = new JTextField();
        JButton btnAdicionar = new JButton("Adicionar");

        panel.add(lblTitulo);
        panel.add(txtTitulo);
        panel.add(lblAutor);
        panel.add(txtAutor);
        panel.add(new JLabel());
        panel.add(btnAdicionar);

        btnAdicionar.addActionListener(e -> {
            String titulo = txtTitulo.getText();
            String autor = txtAutor.getText();

            if (!titulo.isEmpty() && !autor.isEmpty()) {
                Biblioteca.adicionarLivro(titulo, autor);
                JOptionPane.showMessageDialog(this, "Livro adicionado com sucesso!");
                dispose();
            } else {
                JOptionPane.showMessageDialog(this, "Todos os campos são obrigatórios.");
            }
        });

        add(panel);
        setVisible(true);
    }
}

// Tela Listar Livros
class TelaListarLivros extends JFrame {
    public TelaListarLivros() {
        setTitle("Listar Livros");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());

        JTextArea textArea = new JTextArea();
        textArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(textArea);

        for (Livro livro : Biblioteca.getLivros()) {
            textArea.append(livro.toString() + "\n");
        }

        panel.add(scrollPane, BorderLayout.CENTER);
        add(panel);
        setVisible(true);
    }
}

// Tela Emprestar Livro
class TelaEmprestarLivro extends JFrame {
    public TelaEmprestarLivro() {
        setTitle("Emprestar Livro");
        setSize(300, 200);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel(new BorderLayout());
        JTextField txtTitulo = new JTextField();
        JButton btnEmprestar = new JButton("Emprestar");

        panel.add(new JLabel("Digite o título do livro:"), BorderLayout.NORTH);
        panel.add(txtTitulo, BorderLayout.CENTER);
        panel.add(btnEmprestar, BorderLayout.SOUTH);

        btnEmprestar.addActionListener(e -> {
            String titulo = txtTitulo.getText();
            for (Livro livro : Biblioteca.getLivros()) {
                if (livro.getTitulo().equalsIgnoreCase(titulo)) {
                    if (!livro.isEmprestado()) {
                        livro.emprestar();
                        JOptionPane.showMessageDialog(this, "Livro emprestado com sucesso!");
                        dispose();
                        return;
                    } else {
                        JOptionPane.showMessageDialog(this, "O livro já está emprestado.");
                        return;
                    }
                }
            }
            JOptionPane.showMessageDialog(this, "Livro não encontrado.");
        });

        add(panel);
        setVisible(true);
    }
}

// Tela Devolver Livro
class TelaDevolverLivro extends JFrame {
    public TelaDevolverLivro() {
        setTitle("Devolver Livro");
        setSize(300, 200);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel(new BorderLayout());
        JTextField txtTitulo = new JTextField();
        JButton btnDevolver = new JButton("Devolver");

        panel.add(new JLabel("Digite o título do livro:"), BorderLayout.NORTH);
        panel.add(txtTitulo, BorderLayout.CENTER);
        panel.add(btnDevolver, BorderLayout.SOUTH);

        btnDevolver.addActionListener(e -> {
            String titulo = txtTitulo.getText();
            for (Livro livro : Biblioteca.getLivros()) {
                if (livro.getTitulo().equalsIgnoreCase(titulo)) {
                    if (livro.isEmprestado()) {
                        livro.devolver();
                        JOptionPane.showMessageDialog(this, "Livro devolvido com sucesso!");
                        dispose();
                        return;
                    } else {
                        JOptionPane.showMessageDialog(this, "O livro não está emprestado.");
                        return;
                    }
                }
            }
            JOptionPane.showMessageDialog(this, "Livro não encontrado.");
        });

        add(panel);
        setVisible(true);
    }
}
