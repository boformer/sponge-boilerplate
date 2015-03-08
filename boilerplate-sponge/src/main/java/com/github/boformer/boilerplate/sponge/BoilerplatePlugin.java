/*
 * This file is part of Boilerplate, licensed under the MIT License (MIT).
 *
 * Copyright (c) 2015 Felix Schmidt <http://homepage.rub.de/Felix.Schmidt-c2n/>
 * Copyright (c) contributors
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package com.github.boformer.boilerplate.sponge;

import org.slf4j.Logger;
import org.spongepowered.api.Game;
import org.spongepowered.api.event.state.PreInitializationEvent;
import org.spongepowered.api.plugin.Plugin;
import org.spongepowered.api.service.ProviderExistsException;
import org.spongepowered.api.util.event.Subscribe;

import com.github.boformer.boilerplate.NotificationService;
import com.google.inject.Inject;

@Plugin(id = "boilerplate", name = "Boilerplate", version = "0.1.0-SNAPSHOT")
public class BoilerplatePlugin {

	@Inject
	Game game;

	@Inject
	Logger logger;

	NotificationService provider;

	@Subscribe
	private void onPreInitialization(PreInitializationEvent event) {
		logger.info("Boilerplate plugin loaded! Now add your own code :)");

		logger.info("Registering sample service...");

		provider = new SimpleNotificationService();
		provider.setNotification("Welcome to the server!");

		try {
			// Try to register this service
			game.getServiceManager().setProvider(this,
					NotificationService.class, provider);

		} catch (ProviderExistsException e) {
			logger.info("Sample service was already registered by another plugin :(");

			// Remove reference to free up memory
			provider = null;

			// shut down
			return;
		}

		logger.info("Successfully registered sample service!");

		logger.info("Subscribing to events...");
		game.getEventManager().register(this, provider);
	}
}
