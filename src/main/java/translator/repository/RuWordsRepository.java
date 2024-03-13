package translator.repository;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import translator.entity.RuWord;

import java.util.List;

@Repository
@Transactional
public class RuWordsRepository implements AbstractRepository<RuWord> {

    private final SessionFactory sessionFactory;

    @Autowired
    public RuWordsRepository(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    /**
     * @return all Ru words from the database
     */
    @Override
    public List<RuWord> getAll() {
        Session session = sessionFactory.getCurrentSession();
        return session.createQuery("SELECT word FROM RuWord word", RuWord.class).getResultList();
    }

    /**
     * Returns Ru word with related words from database
     *
     * @param word word to find with word associations
     * @return word if word find otherwise null
     */
    @Override
    public RuWord get(RuWord word) {
        if (word == null) return null;
        Session session = sessionFactory.getCurrentSession();
        Query<RuWord> query =
                session.createQuery("SELECT word FROM RuWord word  WHERE word.word=:ru", RuWord.class);
        query.setParameter("ru", word.getWord());
        try {
            return query.getSingleResult();
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * Saves specified Ru word in the database
     *
     * @param word word to save
     * @return saved word
     */
    @Override
    public RuWord save(RuWord word) {
        Session session = sessionFactory.getCurrentSession();
        session.saveOrUpdate(word);
        return word;
    }

    /**
     * Deletes specified Ru word in the database
     *
     * @param word word to delete
     * @return deleted word
     */
    @Override
    public RuWord delete(RuWord word) {
        if ((word = get(word)) == null) return null;
        Session session = sessionFactory.getCurrentSession();
        session.delete(word);
        return word;
    }
}
