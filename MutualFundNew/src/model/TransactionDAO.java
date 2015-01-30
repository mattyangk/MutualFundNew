package model;

import java.util.Arrays;
import java.util.Comparator;

import org.genericdao.ConnectionPool;
import org.genericdao.DAOException;
import org.genericdao.GenericDAO;
import org.genericdao.MatchArg;
import org.genericdao.RollbackException;

import databeans.TransactionBean;

public class TransactionDAO extends GenericDAO<TransactionBean> {

	public TransactionDAO(ConnectionPool connectionPool, String tableName)
			throws DAOException {
		super(TransactionBean.class, tableName, connectionPool);
	}

	public TransactionBean[] getAllTransactions() throws RollbackException {

		TransactionBean[] transactions = match();
		if(transactions == null || transactions.length==0){
			System.out.println("no transactions");
			return null;
		}
		return transactions;

	}

	public TransactionBean[] readPendingTransInOrder() throws RollbackException {

		TransactionBean[] pendingTransactions = match(MatchArg.equals(
				"is_complete", false));
		if(pendingTransactions == null || pendingTransactions.length==0){
			System.out.println("no pending transactions");
			return null;
		}
		
		sortInAscending(pendingTransactions);
		return pendingTransactions;
	}

	public TransactionBean[] getTransactionsByCustomerId(int id)
			throws RollbackException {

		TransactionBean[] transactions = match(MatchArg.equals("customer_id",
				id));
		if(transactions == null || transactions.length==0){
			System.out.println("no transactions");
			return null;
		}

		sortInDescending(transactions);
		return transactions;
	}



	public static void sortInAscending(TransactionBean[] a) {
		Arrays.sort(a, new Comparator<TransactionBean>() {
			@Override
			public int compare(TransactionBean o1, TransactionBean o2) {
				if (o1 == null) {
					return 1;
				} else if (o2 == null) {
					return -1;
				}
				return o1.getTransaction_date().compareTo(
						o2.getTransaction_date());
			}
		});
	}

	public static void sortInDescending(TransactionBean[] a) {
		Arrays.sort(a, new Comparator<TransactionBean>() {
			@Override
			public int compare(TransactionBean o1, TransactionBean o2) {
				if (o1 == null) {
					return 1;
				} else if (o2 == null) {
					return -1;
				}
				return o2.getTransaction_date().compareTo(
						o1.getTransaction_date());
			}
		});
	}

}
