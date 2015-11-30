/**
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.1
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package orderMatcher;
/*
 * @author Qiaochu Chen
 * 
 * This is the main class for the whole logic on this order matcher assignment.
 */
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;

public class OrderMatcher {

	private ArrayList<Item> sellList = new ArrayList();
	private ArrayList<Item> buyList = new ArrayList();

	public static void main(String[] args) {
		OrderMatcher orderMatcher = new OrderMatcher();
		try {
			orderMatcher.act();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/*
	 * This method which has include sell, buy and print function. And it also include trade algorithm.
	 */
	public void act() throws IOException {
		// Initialize buffer reader.
		BufferedReader sc = new BufferedReader(new InputStreamReader(System.in));
		String input = "";
		while (input.equalsIgnoreCase("quit") == false) {
			input = sc.readLine();
			
			// This is the print function which print out the sell and buy list.
			if (input.equalsIgnoreCase("print")) {
				System.out.println("--- SELL ---");
				if (!sellList.isEmpty()) {
					for (Item sellItem : sellList) {
						System.out.println(sellItem.getCommand().toUpperCase() + " "
								+ Integer.toString(sellItem.getShares()) + "@" + Integer.toString(sellItem.getPrice()));
					}
				}
				System.out.println("--- BUY ---");
				if (!buyList.isEmpty()) {
					for (Item buyItem : buyList) {
						System.out.println(buyItem.getCommand().toUpperCase() + " "
								+ Integer.toString(buyItem.getShares()) + "@" + Integer.toString(buyItem.getPrice()));
					}
				}

			} else {
				//This is command parse which is used for paring the user input.
				String[] command = input.split(" ");
				String action = command[0];
				String value = command[1];
				String[] parseValue = value.split("@");
				int shares = Integer.parseInt(parseValue[0]);
				int price = Integer.parseInt(parseValue[1]);
				Item item = new Item(action, shares, price);
				//This is the logic of buy function.
				if (action.equalsIgnoreCase("buy")) {
					if (sellList.isEmpty()) {
						buyList.add(item);
						Collections.sort(buyList);
					} else {
						if (price < sellList.get(0).getPrice()) {
							buyList.add(item);
							Collections.sort(buyList);
						} else {

							ArrayList<Integer> removeIndex = new ArrayList();
							for (Item print : sellList) {

								if (print.getPrice() < price || print.getPrice() == price) {
									int amount = print.getShares();
									int tradeamount;
									if (amount > item.getShares()) {
										int left = amount - item.getShares();
										print.setShares(left);

										tradeamount = item.getShares();
										item.setShares(0);
									} else {
										int buyLeft = item.getShares() - amount;
										tradeamount = amount;
										removeIndex.add(sellList.indexOf(print));
										print.setShares(0);
										item.setShares(buyLeft);
									}

									if (tradeamount > 0) {
										System.out.println("TRADE " + Integer.toString(tradeamount) + "@"
												+ Integer.toString(print.getPrice()));
									}
								}

							}
							if (item.getShares() > 0) {
								buyList.add(item);
								Collections.sort(buyList);
							}
							Iterator<Item> removeList = sellList.iterator();
							while (removeList.hasNext()) {
								Item o = removeList.next();
								if (o.getShares() == 0) {
									removeList.remove();
								}
							}
						}
					}
				}
				// This is the logic of sell function.
				else if (action.equalsIgnoreCase("sell")) {
					if (buyList.isEmpty()) {
						sellList.add(item);
						Collections.sort(sellList);
					} else {
						if (price > buyList.get(0).getPrice()) {
							sellList.add(item);
							Collections.sort(sellList);
						} else {

							ArrayList<Integer> removeIndex = new ArrayList();
							for (Item print : buyList) {

								if (print.getPrice() > price || print.getPrice() == price) {
									int amount = print.getShares();
									int tradeamount;
									if (amount > item.getShares()) {
										int left = amount - item.getShares();
										print.setShares(left);

										tradeamount = item.getShares();
										item.setShares(0);

									} else {
										int sellLeft = item.getShares() - amount;
										tradeamount = amount;
										removeIndex.add(buyList.indexOf(print));
										print.setShares(0);
										item.setShares(sellLeft);
									}
									if (tradeamount > 0) {
										System.out.println("TRADE " + Integer.toString(tradeamount) + "@"
												+ Integer.toString(print.getPrice()));
									}
								}

							}
							if (item.getShares() > 0) {
								sellList.add(item);
								Collections.sort(sellList);
							}
							Iterator<Item> removeList = buyList.iterator();
							while (removeList.hasNext()) {
								Item o = removeList.next();
								if (o.getShares() == 0) {
									removeList.remove();
								}
							}
						}
					}
				}
			}
		}
	}
}
