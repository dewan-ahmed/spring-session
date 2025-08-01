/*
 * Copyright 2014-present the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.springframework.session.mongodb.examples.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author Pool Dolorier
 */
public class HomePage extends BasePage {

	@FindBy(css = "input[type='submit']")
	private WebElement submit;

	public HomePage(WebDriver driver) {
		super(driver);
	}

	public static HomePage go(WebDriver driver) {

		get(driver, "/");
		return PageFactory.initElements(driver, HomePage.class);
	}

	public LoginPage unauthenticated() {
		return LoginPage.go(getDriver());
	}

	public LoginPage logout() {

		this.submit.click();
		return LoginPage.go(getDriver());
	}

	public void assertAt() {
		assertThat(getDriver().getTitle()).isEqualTo("Spring Session Sample - Secured Content");
	}

}
