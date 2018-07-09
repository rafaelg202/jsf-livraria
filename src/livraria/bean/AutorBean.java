package livraria.bean;

import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import livraria.dao.DAO;
import livraria.modelo.Autor;
import livraria.util.RedirectView;



@ManagedBean
@ViewScoped
public class AutorBean {

	private Autor autor = new Autor();

	public Autor getAutor() {
		return autor;
	}
	
	public List<Autor> getAutores(){
		return new DAO<Autor>(Autor.class).listaTodos();
	}

	public RedirectView gravar() {
		System.out.println("Gravando autor " + this.autor.getNome());
		
		if(this.autor.getId() == null){
		new DAO<Autor>(Autor.class).adiciona(this.autor);
		}else {
			new DAO<Autor>(Autor.class).atualiza(autor);
		}
		
		this.autor = new Autor();
		
		return new RedirectView("livro");

	}
	
	public void carregar(Autor autor) {
		System.out.println("Carergando dados do Autor");
		this.autor = autor;
	}
	
	public void remover(Autor autor) {
		
		new DAO<Autor>(Autor.class).remove(autor);
	}
}
