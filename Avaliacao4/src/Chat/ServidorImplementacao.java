package Chat;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;

public class ServidorImplementacao extends UnicastRemoteObject implements Servidor {

    private ArrayList<Cliente> pessoas = new ArrayList<Cliente>();

    protected ServidorImplementacao() throws RemoteException {
	super();
    }

    // Método remoto do objeto real, o qual é chamado pelo stub no cliente e
    // redirecionado pelo skeleton do rmi a implementação
    @Override
    public void registrar(String nome) throws RemoteException {
	System.out.println("Nome:" + nome);
	int identificador = pessoas.size() + 1;
	Cliente pessoa = new ClienteImplementacao(identificador, nome);
	pessoas.add(pessoa);

	System.out.println("Pessoa registrada com sucesso!!\n\nAproveite o chat!!");
    }

    /**
     * Implementação real do método, o qual é chamado pelo stub no cliente e repassa
     * ao objeto real pelo skeleton no servidor
     */
    @Override
    public void enviarMsgDireta(String mensagem, int identificador) throws RemoteException {

	int i = 0;
	while (i < pessoas.size()) {

	    if (pessoas.get(i).getIdentificador() == identificador) {

		Cliente pessoa = (Cliente) pessoas.get(i);

		pessoa.exibirMsg(mensagem, pessoas.get(i).getNome());

		break;
	    }

	    i++;

	}

    }

    /*
     * Implementação real do método, o qual é chamado pelo skeleton.
     */
    @Override
    public void enviarMsgParaTodos(String mensagem) throws RemoteException {
	int i = 0;
	while (i < pessoas.size()) {

	    Cliente pessoa = (Cliente) pessoas.get(i);

	    pessoa.exibirMsg(mensagem, pessoas.get(i).getNome());

	    i++;

	}

    }

    public static void main(String[] args) {

	try {

	    // Cria o registro rmi
	    Registry registry = LocateRegistry.createRegistry(8000);

	    // Cria instância de objeto remoto
	    Servidor servidor = new ServidorImplementacao();

	    // Registra um nome no registro rmi para ser acessado pelo cliente
	    registry.bind("server", servidor);

	} catch (Exception e) {

	    System.out.println("Error: " + e.getMessage());
	}

    }
}
