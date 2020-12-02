package database.dao.daoImpl;

import database.dao.DAO;
import entity.Address;
import entity.Contact;
import type.FamilyStatusType;
import type.SexType;

import java.sql.*;
import java.time.LocalDate;

public class ContactDao implements DAO {

    private final AddressDao dao;

    public ContactDao(AddressDao dao) {
        this.dao = dao;
    }

    public Long save(Contact entity) {

        Long id = 0L;

        try (Connection connection = connect()) {

            final String INSERT_SQL = "INSERT INTO contact (first_name, last_name, middle_name, birthday, gender," +
                    "citizenship, family_status, web_site, email, current_place_of_work, address_id)"
                    + "VALUES (?,?,?,?,?,?,?,?,?,?,?)";

            Date birthday = Date.valueOf(entity.getBirthday());

           PreparedStatement preparedStatement = connection.prepareStatement(INSERT_SQL, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setString(1, entity.getFirstName());
            preparedStatement.setString(2, entity.getLastName());
            preparedStatement.setString(3, entity.getMiddleName());
            preparedStatement.setDate(4, birthday);
            preparedStatement.setString(5, entity.getGender().name());
            preparedStatement.setString(6, entity.getCitizenship());
            preparedStatement.setString(7, entity.getFamilyStatus().name());
            preparedStatement.setString(8, entity.getWebSite());
            preparedStatement.setString(9, entity.getEmail());
            preparedStatement.setString(10, entity.getCurrentPlaceOfWork());
            preparedStatement.setLong(11, dao.save(entity.getAddress()));

            int affectedRows = preparedStatement.executeUpdate();
            if (affectedRows > 0) {
                try (ResultSet resultSet = preparedStatement.getGeneratedKeys()) {
                    if (resultSet.next()) {
                        id = resultSet.getLong(1);
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return id;
    }


    public void delete(Long id) {
        String DELETE_SQL = "DELETE FROM contact WHERE id=?";
        try (Connection connection = connect()) {
            PreparedStatement preparedStatement = connection.prepareStatement(DELETE_SQL);
            preparedStatement.setLong(1, id);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }


    public Contact update(Contact entity) {
        String UPDATE_SQL = "UPDATE contact SET first_name=?, last_name=?, middle_name=?, birthday=?, gender=?," +
                "citizenship=?, family_status=?, web_site=?, email=?, current_place_of_work=?, address_id=? WHERE id=?";
        Date birthday = Date.valueOf(entity.getBirthday());
        try (Connection connection = connect()) {
            PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_SQL);
            preparedStatement.setString(1, entity.getFirstName());
            preparedStatement.setString(2, entity.getLastName());
            preparedStatement.setString(3, entity.getMiddleName());
            preparedStatement.setDate(4, birthday);
            preparedStatement.setString(5, entity.getGender().name());
            preparedStatement.setString(6, entity.getCitizenship());
            preparedStatement.setString(7, entity.getFamilyStatus().name());
            preparedStatement.setString(8, entity.getWebSite());
            preparedStatement.setString(9, entity.getEmail());
            preparedStatement.setString(10, entity.getCurrentPlaceOfWork());
            preparedStatement.setLong(11, entity.getAddress().getId());
            preparedStatement.setLong(12, entity.getId());
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    entity.setId(resultSet.getLong(1));
                    entity.setFirstName(resultSet.getString("first_name"));
                    entity.setLastName(resultSet.getString("last_name"));
                    entity.setMiddleName(resultSet.getString("middle_name"));
                    entity.setBirthday(LocalDate.parse(resultSet.getString("birthday")));
                    entity.setGender(SexType.valueOf(resultSet.getString("gender")));
                    entity.setCitizenship(resultSet.getString("citizenship"));
                    entity.setFamilyStatus(FamilyStatusType.valueOf(resultSet.getString("family_status")));
                    entity.setWebSite(resultSet.getString("web_site"));
                    entity.setEmail(resultSet.getString("email"));
                    entity.setCurrentPlaceOfWork(resultSet.getString("current_place_of_work"));
                    Long addressId = resultSet.getLong("address_id");
                    entity.setAddress(dao.read(addressId));
                }

            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return entity;
    }


    public Contact read(Long contactId) {
        String SELECT_SQL = "SELECT * FROM contact WHERE id=?";

        Contact contact = null;

        try (Connection connection = connect()) {
            PreparedStatement preparedStatement = connection.prepareStatement(SELECT_SQL);
            preparedStatement.setLong(1, contactId);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    contact = new Contact();

                    contact.setId(resultSet.getLong(1));
                    contact.setFirstName(resultSet.getString("first_name"));
                    contact.setLastName(resultSet.getString("last_name"));
                    contact.setMiddleName(resultSet.getString("middle_name"));
                    contact.setBirthday(LocalDate.parse(resultSet.getString("birthday")));
                    contact.setGender(SexType.valueOf(resultSet.getString("gender")));
                    contact.setCitizenship(resultSet.getString("citizenship"));
                    contact.setFamilyStatus(FamilyStatusType.valueOf(resultSet.getString("family_status")));
                    contact.setWebSite(resultSet.getString("web_site"));
                    contact.setEmail(resultSet.getString("email"));
                    contact.setCurrentPlaceOfWork(resultSet.getString("current_place_of_work"));
                    Long addressId = resultSet.getLong("address_id");
                    Address address = dao.read(addressId);
                    contact.setAddress(address);

                }

            } catch (SQLException e) {
                e.printStackTrace();
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return contact;
    }
}
