package edu.kennesaw.seniorproject.opensale.entities;

// TODO: Add actual user fields, methods.
import edu.common.Exceptions.InsufficentPermissionException;
import edu.common.UserObjects.EUserTypes;
import edu.common.Permissions.Permissions;
import edu.common.UserObjects.User;
import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;

/**
 * User entity.
 *
 * @author spencer
 */
@Entity
@NamedQueries({
    @NamedQuery(
        name = "UserEntity.findUserByUsername",
        query = "select u from UserEntity u where u.userName = :username"
    ), // Used for editing users -- fetch a user by username
    @NamedQuery(
        name = "UserEntity.findUserByLogin",
        query = "select u from UserEntity u where u.userName = :username and u.password = :password"
    ), // Used for login -- fetch a user by username and (hashed) password
    @NamedQuery(
        name = "UserEntity.getEditableUsers",
        query = "select u from UserEntity u where u.userType <= :loggedInUserType"
    ), // Used for User Management screen -- users may only edit lower-role users
    @NamedQuery(
        name = "UserEntity.findManagerByLogin",
        query = "select u from UserEntity u where u.userName = :username and u.password = :password and u.userType >= edu.common.UserObjects.EUserTypes.Manager"
    ) // Used to verify authorization of Refund transactions
})
public class UserEntity extends User implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    public UserEntity() { }
    
    /**
     * Creates a new user, checking to make sure that the currently-logged in 
     * user has permission to do so.
     * @param manager
     * @throws InsufficentPermissionException 
     */
    public UserEntity(User manager) throws InsufficentPermissionException {
        if (!manager.isAllowed(User.class)) {
            throw new InsufficentPermissionException();
        }
    }

    /*
     * Use this constructor when making new Users.
     */
    public UserEntity(String userName, String password, EUserTypes userType, Permissions permission, User manager) throws InsufficentPermissionException {
            if (manager.isAllowed(User.class)) {
                this.userName = userName;
                this.password = password;
                this.userType = userType;
                this.permissions = permission;
            } else {
                throw new InsufficentPermissionException();
            }
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (userName != null ? userName.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof UserEntity)) {
            return false;
        }
        UserEntity other = (UserEntity) object;
        if ((this.userName == null && other.userName != null) || (this.userName != null && !this.userName.equals(other.userName))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "edu.kennesaw.seniorproject.opensale.model.User[ id=" + userName + " ]";
    }
}
