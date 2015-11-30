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
 * 
 * This is the item for each sell or buy object in ArrayList.
 */
public class Item implements Comparable<Item> {

	private String command;
	private int shares;
	private int price;

	public Item(String command, int shares, int price) {
		this.command = command;
		this.shares = shares;
		this.price = price;
	}

	public int compareTo(Item other) {
		if (command.equalsIgnoreCase("sell")) {
			return price == other.price ? 0 : price > other.price ? 1 : -1;
		} else {
			return price == other.price ? 0 : price < other.price ? 1 : -1;

		}
	}

	public void setShares(int shares) {
		this.shares = shares;
	}

	public String getCommand() {
		return command;
	}

	public int getShares() {
		return shares;
	}

	public int getPrice() {
		return price;
	}
}
