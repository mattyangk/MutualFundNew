package model;

import org.genericdao.ConnectionPool;
import org.genericdao.DAOException;
import org.genericdao.GenericDAO;
import org.genericdao.MatchArg;
import org.genericdao.RollbackException;
import org.genericdao.Transaction;

import util.ConvertUtil;
import databeans.PositionBean;
import databeans.TransactionBean;
import exception.AmountOutOfBoundException;
import exception.SharesOutOfBoundException;

public class PositionDAO extends GenericDAO<PositionBean> {
	public PositionDAO(ConnectionPool connectionPool, String tableName)
			throws DAOException {
		super(PositionBean.class, tableName, connectionPool);
	}
	
	public PositionBean[] getPositionsByCustomerId(int id)
			throws RollbackException {

		PositionBean[] positions = match(MatchArg.equals("customer_id",
				id));
		return positions;
	}

	public void updateAvailableShares(int fund_id, int customer_id, double sellingShares)
			throws RollbackException, SharesOutOfBoundException {
		try {
			Transaction.begin();
			PositionBean position = read(fund_id, customer_id);
			if (position == null) {
				throw new RollbackException("This position:" + "fund_id:"
						+ fund_id + "customer_id" + customer_id
						+ " does not exist");
			} else {
				double availableShares = ConvertUtil.convertShareLongToDouble(position.getAvailable_shares());
				double newAvailableShares = availableShares - sellingShares;
				if (newAvailableShares < 0)
					throw new SharesOutOfBoundException(availableShares,
							sellingShares);
				else {
					position.setAvailable_shares(ConvertUtil.coverShareDoubleToLong(newAvailableShares));
					update(position);
				}
			}
			Transaction.commit();
		} finally {
			if (Transaction.isActive())
				Transaction.rollback();
		}
	}
	
	public void updateShares(int fund_id, int customer_id, double shares)
			throws RollbackException {
		try {
			Transaction.begin();
			PositionBean position = read(fund_id, customer_id);
			if (position == null) {
				throw new RollbackException("Customer does not own this shares !");
			} else {
				
					position.setShares(ConvertUtil.coverShareDoubleToLong(shares));
					position.setAvailable_shares(ConvertUtil.coverShareDoubleToLong(shares));
					update(position);
				
			}
			Transaction.commit();
		} finally {
			if (Transaction.isActive())
				Transaction.rollback();
		}
	}

}
