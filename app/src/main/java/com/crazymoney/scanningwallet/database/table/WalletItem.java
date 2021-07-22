package com.crazymoney.scanningwallet.database.table;

import java.io.Serializable;

public class WalletItem implements Serializable, Comparable<WalletItem> {

	private String name;
	private long balance;
	private long contractDecimals;
	private double quote;
	private double quoteRate;
	private String logo;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setBalance(long balance) {
		this.balance = balance;
	}

	public void setContractDecimals(long contractDecimals) {
		this.contractDecimals = contractDecimals;
	}

	public double getQuote() {
		return quote;
	}

	public void setQuote(double quote) {
		this.quote = quote;
	}

	public void setQuoteRate(double quoteRate) {
		this.quoteRate = quoteRate;
	}

	public String getLogo() {
		return logo;
	}

	public void setLogo(String logo) {
		this.logo = logo;
	}

	public String formatBalance() {
		double realBalance = balance;
		for (int i = 0; i < this.contractDecimals; i++) {
			realBalance = realBalance / 10;
		}
		return String.format("%,.4f", realBalance);
	}

	public String formatUsdValue() {
		return String.format("%,.2f", this.quote);
	}

	@Override
	public String toString() {
		return "WalletItem{" +
				"name='" + name + '\'' +
				", balance=" + balance +
				", contractDecimals=" + contractDecimals +
				", quote=" + quote +
				", quoteRate=" + quoteRate +
				", logo='" + logo + '\'' +
				'}';
	}

	@Override
	public int compareTo(WalletItem item) {
		return Double.compare(item.getQuote(), this.quote);
	}
}
