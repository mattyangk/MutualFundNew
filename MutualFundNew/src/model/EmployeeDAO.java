package model;

import org.genericdao.ConnectionPool;
import org.genericdao.DAOException;
import org.genericdao.GenericDAO;
import org.genericdao.MatchArg;
import org.genericdao.RollbackException;
import org.genericdao.Transaction;

import databeans.EmployeeBean;

public class EmployeeDAO extends GenericDAO<EmployeeBean>{
	public EmployeeDAO(ConnectionPool connectionPool, String tableName)
			throws DAOException {
		super(EmployeeBean.class, tableName, connectionPool);
	}

	public EmployeeBean getEmployeeByUsername(String username)
			throws RollbackException {
		EmployeeBean[] employee = match(MatchArg.equalsIgnoreCase("username", username));
		if (employee.length != 1) {
			System.out.println("not correct number of employees");
			return null;
		}
		return employee[0];
	}
	
	public void updatePassword (int id, String oldPassword, String newPassword) throws RollbackException {
		try {
			Transaction.begin();
			EmployeeBean employee = read(id);
			if (employee.getPassword().equals(oldPassword)) {
				employee.setPassword(newPassword);
				update(employee);
			} else {
				throw new RollbackException("Old passowrd is not correct");
			}
			Transaction.commit();
		} finally {
			if (Transaction.isActive())
				Transaction.rollback();
		}
	}

}
