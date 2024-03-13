package translator.repository;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import translator.entity.EnWord;

import java.util.List;

@Repository
@Transactional
public class EnWordsRepository implements AbstractRepository<EnWord> {

    private final SessionFactory sessionFactory;

    @Autowired
    public EnWordsRepository(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    /**
     * @return all Ru words from the database
     */

    @Override
    public List<EnWord> getAll() {
        Session session = sessionFactory.getCurrentSession();
        return session.createQuery("SELECT word FROM EnWord word", EnWord.class).getResultList();
    }

    /**
     * Returns En word with related words from database
     *
     * @param word word to find with word associations
     * @return word if word find otherwise null
     */
    @Override
    public EnWord get(EnWord word) {
        if (word == null) return null;
        Session session = sessionFactory.getCurrentSession();
        Query<EnWord> query =
                session.createQuery("SELECT word FROM EnWord word  WHERE word.word=:en", EnWord.class);
        query.setParameter("en", word.getWord());
        try {
            return query.getSingleResult();
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * Saves specified En word in the database
     *
     * @param word word to save
     * @return saved word
     */
    @Override
    public EnWord save(EnWord word) {
        Session session = sessionFactory.getCurrentSession();
        session.save(word);
        return word;
    }

    /**
     * Deletes specified En word in the database
     *
     * @param word word to delete
     * @return deleted word
     */
    @Override
    public EnWord delete(EnWord word) {
        if ((word = get(word)) == null) return null;
        Session session = sessionFactory.getCurrentSession();
        session.delete(word);
        return word;
    }
}