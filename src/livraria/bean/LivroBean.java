package livraria.bean;

import javax.faces.bean.ManagedBean;

import livraria.dao.DAO;
import livraria.modelo.Livro;

@ManagedBean
public class LivroBean {

	private Livro livro = new Livro();
	
	public Livro getLivro() {
		return livro;
	}

	public void gravar() {
		System.out.println("Gravando livro! " + this.livro.getTitulo());
		
		if(livro.getAutores().isEmpty()) {
			throw new RuntimeException("Livro deve ter pelo menos um autor");
		}
		new DAO<Livro>(Livro.class).adiciona(this.livro);
	}
}
