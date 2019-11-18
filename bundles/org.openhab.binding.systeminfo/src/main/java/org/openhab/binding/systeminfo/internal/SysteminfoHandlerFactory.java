/**
 * Copyright (c) 2010-2019 Contributors to the openHAB project
 *
 * See the NOTICE file(s) distributed with this work for additional
 * information.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0
 *
 * SPDX-License-Identifier: EPL-2.0
 */
package org.openhab.binding.systeminfo.internal;

import static org.openhab.binding.systeminfo.internal.SysteminfoBindingConstants.THING_TYPE_COMPUTER;

import java.util.Collections;
import java.util.Set;

import org.eclipse.jdt.annotation.NonNullByDefault;
import org.eclipse.jdt.annotation.Nullable;
import org.eclipse.smarthome.core.thing.Thing;
import org.eclipse.smarthome.core.thing.ThingTypeUID;
import org.eclipse.smarthome.core.thing.binding.BaseThingHandlerFactory;
import org.eclipse.smarthome.core.thing.binding.ThingHandler;
import org.eclipse.smarthome.core.thing.binding.ThingHandlerFactory;
import org.openhab.binding.systeminfo.internal.handler.SysteminfoHandler;
import org.openhab.binding.systeminfo.internal.model.SysteminfoInterface;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * The {@link SysteminfoHandlerFactory} is responsible for creating things and thing
 * handlers.
 *
 * @author Svilen Valkanov - Initial contribution
 * @author Lyubomir Papazov - Pass systeminfo service to the SysteminfoHandler constructor
 * @author Wouter Born - Add null annotations
 */
@NonNullByDefault
@Component(service = ThingHandlerFactory.class, configurationPid = "binding.systeminfo")
public class SysteminfoHandlerFactory extends BaseThingHandlerFactory {

    private static final Set<ThingTypeUID> SUPPORTED_THING_TYPES_UIDS = Collections.singleton(THING_TYPE_COMPUTER);

    private @NonNullByDefault({}) SysteminfoInterface systeminfo;

    @Override
    public boolean supportsThingType(ThingTypeUID thingTypeUID) {
        return SUPPORTED_THING_TYPES_UIDS.contains(thingTypeUID);
    }

    @Override
    protected @Nullable ThingHandler createHandler(Thing thing) {
        ThingTypeUID thingTypeUID = thing.getThingTypeUID();

        if (thingTypeUID.equals(THING_TYPE_COMPUTER)) {
            return new SysteminfoHandler(thing, systeminfo);
        }

        return null;
    }

    @Reference
    public void bindSystemInfo(SysteminfoInterface systeminfo) {
        this.systeminfo = systeminfo;
    }

    public void unbindSystemInfo(SysteminfoInterface systeminfo) {
        this.systeminfo = null;
    }
}
