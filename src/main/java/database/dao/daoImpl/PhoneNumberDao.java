package database.dao.daoImpl;

import database.dao.DAO;
import entity.PhoneNumber;
import exception.SQLExceptionDao;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import type.TypeNumber;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PhoneNumberDao implements DAO {
    final String INSERT_SQL = "INSERT INTO phone_number (code_of_country, operator_code, number, type, comment, contact_id)" +
            " VALUES (?,?,?,?,?,?)";
    final String UPDATE_SQL = "UPDATE phone_number SET code_of_country=?, operator_code=?, number=?, type=?, comment=?," +
            " contact_id=? WHERE id=?";

    final String DELETE_SQL = "DELETE FROM phone_number WHERE contact_id=?";
    final String SELECT_SQL = "SELECT * FROM phone_number WHERE id=?";
    final String SELECT_BY_CONTACT_ID_SQL = "SELECT * FROM phone_number WHERE contact_id=?";
    final Logger logger = LoggerFactory.getLogger(PhoneNumberDao.class);

    public PhoneNumber save(PhoneNumber entity, Long contactId, Connection connection) throws SQLExceptionDao {
        Long id = 0L;
        try (PreparedStatement preparedStatement = connection.prepareStatement(INSERT_SQL, Statement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setInt(1, entity.getCodeOfCountry());
            preparedStatement.setInt(2, entity.getOperatorCode());
            preparedStatement.setString(3, entity.getNumber());
            preparedStatement.setString(4, entity.getType().name());
            preparedStatement.setString(5, entity.getComment());
            preparedStatement.setLong(6, contactId);

            int affectedRows = preparedStatement.executeUpdate();
            if (affectedRows > 0) {
                try (ResultSet resultSet = preparedStatement.getGeneratedKeys()) {
                    if (resultSet.next()) {
                        id = resultSet.getLong(1);
                    }
                } catch (SQLException e) {
                    logger.error(e.getMessage());
                    connection.rollback();
                    throw new SQLExceptionDao("Exception in save phoneNumber.");
                }
            }
        } catch (SQLException e) {
            logger.error(e.getMessage());
            try {
                connection.rollback();
                throw new SQLExceptionDao("Exception in save phoneNumber.");
            } catch (SQLException exception) {
                logger.error(exception.getMessage());
                throw new SQLExceptionDao("Exception in save phoneNumber.");
            }
        }

        try (PreparedStatement preparedStatement = connection.prepareStatement(SELECT_SQL)) {
            preparedStatement.setLong(1, id);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    entity.setId(resultSet.getLong(1));
                    entity.setCodeOfCountry(resultSet.getInt("code_of_country"));
                    entity.setOperatorCode(resultSet.getInt("operator_code"));
                    entity.setNumber(resultSet.getString("number"));
                    entity.setType(TypeNumber.valueOf((resultSet.getString("type"))));
                    entity.setComment(resultSet.getString("comment"));
                    entity.setContactId(resultSet.getLong("contact_id"));
                }
            } catch (SQLException e) {
                logger.error(e.getMessage());
                connection.rollback();
                throw new SQLExceptionDao("Exception in save phoneNumber.");

            }
        } catch (SQLException e) {
            logger.error(e.getMessage());
            try {
                connection.rollback();
                throw new SQLExceptionDao("Exception in save phoneNumber.");
            } catch (SQLException exception) {
                logger.error(e.getMessage());
                throw new SQLExceptionDao("Exception in save phoneNumber.");
            }
        }
        return entity;
    }

    public void deleteByContactId(Long contactId, Connection connection) throws SQLExceptionDao {
        try (PreparedStatement preparedStatement = connection.prepareStatement(DELETE_SQL)) {
            preparedStatement.setLong(1, contactId);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            logger.error(e.getMessage());
            try {
                connection.rollback();
                throw new SQLExceptionDao("Exception in deleteByContactId phoneNumber.");

            } catch (SQLException exception) {
                logger.error(exception.getMessage());
                throw new SQLExceptionDao("Exception in deleteByContactId phoneNumber.");
            }
        }
    }

    public PhoneNumber update(PhoneNumber entity, Long contactId, Connection connection) throws SQLExceptionDao{

        try (PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_SQL)) {
            preparedStatement.setInt(1, entity.getCodeOfCountry());
            preparedStatement.setInt(2, entity.getOperatorCode());
            preparedStatement.setString(3, entity.getNumber());
            preparedStatement.setString(4, entity.getType().name());
            preparedStatement.setString(5, entity.getComment());
            preparedStatement.setLong(6, contactId);
            preparedStatement.setLong(7, entity.getId());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            logger.error(e.getMessage());
            try {
                connection.rollback();
                throw new SQLExceptionDao("Exception in update phoneNumber.");
            } catch (SQLException exception) {
                logger.error(exception.getMessage());
                throw new SQLExceptionDao("Exception in update phoneNumber.");
            }
        }

        try (PreparedStatement preparedStatement = connection.prepareStatement(SELECT_SQL)) {
            preparedStatement.setLong(1, entity.getId());
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    entity.setId(resultSet.getLong(1));
                    entity.setCodeOfCountry(resultSet.getInt("code_of_country"));
                    entity.setOperatorCode(resultSet.getInt("operator_code"));
                    entity.setNumber(resultSet.getString("number"));
                    entity.setType(TypeNumber.valueOf((resultSet.getString("type"))));
                    entity.setComment(resultSet.getString("comment"));
                    entity.setContactId(resultSet.getLong("contact_id"));
                }
            } catch (SQLException e) {
                logger.error(e.getMessage());
                connection.rollback();
                throw new SQLExceptionDao("Exception in update phoneNumber.");
            }
        } catch (SQLException e) {
            logger.error(e.getMessage());
            try {
                connection.rollback();
                throw new SQLExceptionDao("Exception in update phoneNumber.");
            } catch (SQLException exception) {
                logger.error(e.getMessage());
                throw new SQLExceptionDao("Exception in update phoneNumber.");
            }
        }
        return entity;

    }

    public PhoneNumber read(Long phoneNumberId, Connection connection) throws SQLExceptionDao {

        PhoneNumber entity = null;
        try (PreparedStatement preparedStatement = connection.prepareStatement(SELECT_SQL)) {
            preparedStatement.setLong(1, phoneNumberId);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    entity = new PhoneNumber();
                    entity.setId(resultSet.getLong(1));
                    entity.setCodeOfCountry(resultSet.getInt("code_of_country"));
                    entity.setOperatorCode(resultSet.getInt("operator_code"));
                    entity.setNumber(resultSet.getString("number"));
                    entity.setType(TypeNumber.valueOf((resultSet.getString("type"))));
                    entity.setComment(resultSet.getString("comment"));
                    entity.setContactId(resultSet.getLong("contact_id"));
                }
            } catch (SQLException e) {
                logger.error(e.getMessage());
                throw new SQLExceptionDao("Exception in read phoneNumber.");
            }
        } catch (SQLException e) {
            logger.error(e.getMessage());
            throw new SQLExceptionDao("Exception in read phoneNumber.");

        }
        return entity;
    }

    public List<PhoneNumber> getAllPhoneNumberByContactId(Long contactId, Connection connection) throws SQLExceptionDao{
        List<PhoneNumber> phoneNumbers = new ArrayList<>();
        try (PreparedStatement preparedStatement = connection.prepareStatement(SELECT_BY_CONTACT_ID_SQL)) {
            preparedStatement.setLong(1, contactId);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    PhoneNumber entity = new PhoneNumber();
                    entity.setId(resultSet.getLong(1));
                    entity.setCodeOfCountry(resultSet.getInt("code_of_country"));
                    entity.setOperatorCode(resultSet.getInt("operator_code"));
                    entity.setNumber(resultSet.getString("number"));
                    entity.setType(TypeNumber.valueOf((resultSet.getString("type"))));
                    entity.setComment(resultSet.getString("comment"));
                    entity.setContactId(resultSet.getLong("contact_id"));
                    phoneNumbers.add(entity);
                }

            } catch (SQLException e) {
                logger.error(e.getMessage());
                throw new SQLExceptionDao("Exception in getAllPhoneNumberByContactId phoneNumber.");
            }
        } catch (SQLException e) {
            logger.error(e.getMessage());
            throw new SQLExceptionDao("Exception in getAllPhoneNumberByContactId phoneNumber.");

        }
        return phoneNumbers;
    }
}
