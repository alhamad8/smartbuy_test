package smart_buy;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import io.github.bonigarcia.wdm.WebDriverManager;

public class smart_buy {
	public WebDriver driver;
	public int numberOfTry = 100;
	public int number_of_items_in_store = 4;
	SoftAssert softassert = new SoftAssert();

	@BeforeTest()
	public void log_in() throws InterruptedException {

		WebDriverManager.chromedriver().setup();
		driver = new ChromeDriver();
		String url = "https://smartbuy-me.com/smartbuystore/";
		driver.get(url);
		driver.manage().window().maximize();
		Thread.sleep(100);
		driver.findElement(By.xpath("/html/body/main/header/div[2]/div/div[2]/a")).click();

	}

	@Test
	public void adding_items_to_cart() {
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
		for (int i = 0; i < numberOfTry; i++) {

			driver.findElement(By.xpath(
					"//*[@id=\"newtab-Featured\"]/div/div[1]/div/div/div/div[2]/div/div[3]/div[1]/div/div/form[1]/div[1]/button"))
					.click();
			String msg = driver.findElement(By.xpath("//*[@id=\"addToCartLayer\"]/div[1]")).getText();

			if (msg.contains("Sorry")) {
				numberOfTry = i;
				driver.findElement(By.xpath("//*[@id=\"addToCartLayer\"]/a[1]")).click();

			} else {
				driver.findElement(By.xpath("//*[@id=\"addToCartLayer\"]/a[2]")).click();

			}

		}

	}

	@Test
	public void check_correct_price() {
		driver.navigate().back();
		String single_item_price = driver
				.findElement(By.xpath(
						"//*[@id=\"newtab-Featured\"]/div/div[1]/div/div/div/div[2]/div/div[2]/div[2]/div/div/span[3]"))
				.getText();
		String[] new_single_item_price = single_item_price.split("JOD");
		String final_single_item_price = new_single_item_price[0].trim();
		String updated_Price = final_single_item_price.replaceAll(",", ".");
		System.out.println(updated_Price);
		double final_price = Double.parseDouble(updated_Price);
		System.out.println(final_price * numberOfTry);
		// String actualTitle = driver.getTitle();
		System.out.println(final_price);
		// check_discount_price_for_samssung+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
		String the_real_price = driver
				.findElement(By.xpath(
						"//*[@id=\"newtab-Featured\"]/div/div[1]/div/div/div/div[2]/div/div[2]/div[2]/div/div/span[2]"))
				.getText();
		String[] real_single_item_price = the_real_price.split("JOD");
		String final_real_single_item_price = real_single_item_price[0].trim();
		String updated_real_Price = final_real_single_item_price.replaceAll(",", ".");
		Double final_price_before = Double.parseDouble(updated_real_Price);
		System.out.println(final_price_before);
		String discount_rate = driver
				.findElement(By.xpath(
						"//*[@id=\"newtab-Featured\"]/div/div[1]/div/div/div/div[2]/div/div[2]/div[2]/div/div/span[1]"))
				.getText();
		String[] new_disc_rate = discount_rate.split("%");
		String updated_disc_rate = new_disc_rate[0].trim();
		Double real_disc_rate = Double.parseDouble(updated_disc_rate);
		System.out.println(real_disc_rate);
		double checked_price = ((100 - real_disc_rate) * final_price_before) / 100;
		double final_checked_price = Math.round(checked_price);
		System.out.println(final_checked_price);
		softassert.assertEquals(final_price, 1200);
		softassert.assertEquals(final_price, final_checked_price);
		softassert.assertAll();
	}

//	@Test()
//	public void check_discount_price_for_samssung() {
//		driver.navigate().back();
//		String the_real_price = driver
//				.findElement(By.xpath(
//						"//*[@id=\"newtab-Featured\"]/div/div[1]/div/div/div/div[2]/div/div[2]/div[2]/div/div/span[2]"))
//				.getText();
//		String[] real_single_item_price = the_real_price.split("JOD");
//		String final_real_single_item_price = real_single_item_price[0].trim();
//		String updated_real_Price = final_real_single_item_price.replaceAll(",", ".");
//		Double final_price_before = Double.parseDouble(updated_real_Price);
//		System.out.println(final_price_before);
//		String discount_rate = driver
//				.findElement(By.xpath(
//						"//*[@id=\"newtab-Featured\"]/div/div[1]/div/div/div/div[2]/div/div[2]/div[2]/div/div/span[1]"))
//				.getText();
//		String[] new_disc_rate = discount_rate.split("%");
//		String updated_disc_rate = new_disc_rate[0].trim();
//		Double real_disc_rate = Double.parseDouble(updated_disc_rate);
//		System.out.println(real_disc_rate);
//		double checked_price = ((100 - real_disc_rate) * final_price_before) / 100;
//		double final_checked_price = Math.round(checked_price);
//		System.out.println(final_checked_price);
//		softassert.assertEquals(final_checked_price, final_price);
//
//	}

}
