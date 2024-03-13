package translator.repository;

import translator.entity.AbstractWord;

import java.util.List;

public interface AbstractRepository<E extends AbstractWord> {
    // get all words from database
    public List<E> getAll();

    // get specified word from database
    public E get(E word);

    // save specified word into database
    public E save(E word);

    // delete specified word from database
    public E delete(E word);
}
