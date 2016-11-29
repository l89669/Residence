/*
 * Decompiled with CFR 0_119.
 * 
 * Could not load the following classes:
 *  org.bukkit.Bukkit
 *  org.bukkit.Location
 *  org.bukkit.Material
 *  org.bukkit.World
 *  org.bukkit.block.Block
 *  org.bukkit.entity.Player
 *  org.bukkit.util.Vector
 */
package com.bekvon.bukkit.residence.utils;

import com.bekvon.bukkit.residence.utils.ReflectionUtils;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

public enum ParticleEffects {
    EXPLOSION_NORMAL("explode", 0, -1, new ParticleProperty[]{ParticleProperty.DIRECTIONAL}),
    EXPLOSION_LARGE("largeexplode", 1, -1, new ParticleProperty[0]),
    EXPLOSION_HUGE("hugeexplosion", 2, -1, new ParticleProperty[0]),
    FIREWORKS_SPARK("fireworksSpark", 3, -1, new ParticleProperty[]{ParticleProperty.DIRECTIONAL}),
    WATER_BUBBLE("bubble", 4, -1, new ParticleProperty[]{ParticleProperty.DIRECTIONAL, ParticleProperty.REQUIRES_WATER}),
    WATER_SPLASH("splash", 5, -1, new ParticleProperty[]{ParticleProperty.DIRECTIONAL}),
    WATER_WAKE("wake", 6, 7, new ParticleProperty[]{ParticleProperty.DIRECTIONAL}),
    SUSPENDED("suspended", 7, -1, new ParticleProperty[]{ParticleProperty.REQUIRES_WATER}),
    SUSPENDED_DEPTH("depthSuspend", 8, -1, new ParticleProperty[]{ParticleProperty.DIRECTIONAL}),
    CRIT("crit", 9, -1, new ParticleProperty[]{ParticleProperty.DIRECTIONAL}),
    CRIT_MAGIC("magicCrit", 10, -1, new ParticleProperty[]{ParticleProperty.DIRECTIONAL}),
    SMOKE_NORMAL("smoke", 11, -1, new ParticleProperty[]{ParticleProperty.DIRECTIONAL}),
    SMOKE_LARGE("largesmoke", 12, -1, new ParticleProperty[]{ParticleProperty.DIRECTIONAL}),
    SPELL("spell", 13, -1, new ParticleProperty[0]),
    SPELL_INSTANT("instantSpell", 14, -1, new ParticleProperty[0]),
    SPELL_MOB("mobSpell", 15, -1, new ParticleProperty[]{ParticleProperty.COLORABLE}),
    SPELL_MOB_AMBIENT("mobSpellAmbient", 16, -1, new ParticleProperty[]{ParticleProperty.COLORABLE}),
    SPELL_WITCH("witchMagic", 17, -1, new ParticleProperty[0]),
    DRIP_WATER("dripWater", 18, -1, new ParticleProperty[0]),
    DRIP_LAVA("dripLava", 19, -1, new ParticleProperty[0]),
    VILLAGER_ANGRY("angryVillager", 20, -1, new ParticleProperty[0]),
    VILLAGER_HAPPY("happyVillager", 21, -1, new ParticleProperty[]{ParticleProperty.DIRECTIONAL}),
    TOWN_AURA("townaura", 22, -1, new ParticleProperty[]{ParticleProperty.DIRECTIONAL}),
    NOTE("note", 23, -1, new ParticleProperty[]{ParticleProperty.COLORABLE}),
    PORTAL("portal", 24, -1, new ParticleProperty[]{ParticleProperty.DIRECTIONAL}),
    ENCHANTMENT_TABLE("enchantmenttable", 25, -1, new ParticleProperty[]{ParticleProperty.DIRECTIONAL}),
    FLAME("flame", 26, -1, new ParticleProperty[]{ParticleProperty.DIRECTIONAL}),
    LAVA("lava", 27, -1, new ParticleProperty[0]),
    FOOTSTEP("footstep", 28, -1, new ParticleProperty[0]),
    CLOUD("cloud", 29, -1, new ParticleProperty[]{ParticleProperty.DIRECTIONAL}),
    REDSTONE("reddust", 30, -1, new ParticleProperty[]{ParticleProperty.COLORABLE}),
    SNOWBALL("snowballpoof", 31, -1, new ParticleProperty[0]),
    SNOW_SHOVEL("snowshovel", 32, -1, new ParticleProperty[]{ParticleProperty.DIRECTIONAL}),
    SLIME("slime", 33, -1, new ParticleProperty[0]),
    HEART("heart", 34, -1, new ParticleProperty[0]),
    BARRIER("barrier", 35, 8, new ParticleProperty[0]),
    ITEM_CRACK("iconcrack", 36, -1, new ParticleProperty[]{ParticleProperty.DIRECTIONAL, ParticleProperty.REQUIRES_DATA}),
    BLOCK_CRACK("blockcrack", 37, -1, new ParticleProperty[]{ParticleProperty.DIRECTIONAL, ParticleProperty.REQUIRES_DATA}),
    BLOCK_DUST("blockdust", 38, 7, new ParticleProperty[]{ParticleProperty.DIRECTIONAL, ParticleProperty.REQUIRES_DATA}),
    WATER_DROP("droplet", 39, 8, new ParticleProperty[0]),
    ITEM_TAKE("take", 40, 8, new ParticleProperty[0]),
    MOB_APPEARANCE("mobappearance", 41, 8, new ParticleProperty[0]);
    
    private static final Map<String, ParticleEffects> NAME_MAP;
    private static final Map<Integer, ParticleEffects> ID_MAP;
    private final String name;
    private final int id;
    private final int requiredVersion;
    private final List<ParticleProperty> properties;

    static {
        NAME_MAP = new HashMap<String, ParticleEffects>();
        ID_MAP = new HashMap<Integer, ParticleEffects>();
        ParticleEffects[] arrparticleEffects = ParticleEffects.values();
        int n = arrparticleEffects.length;
        int n2 = 0;
        while (n2 < n) {
            ParticleEffects effect = arrparticleEffects[n2];
            NAME_MAP.put(effect.name, effect);
            ID_MAP.put(effect.id, effect);
            ++n2;
        }
    }

    private /* varargs */ ParticleEffects(String name, int id, String requiredVersion, int properties, int n2, ParticleProperty ... arrparticleProperty) {
        this.name = name;
        this.id = id;
        this.requiredVersion = requiredVersion;
        this.properties = Arrays.asList(properties);
    }

    public String getName() {
        return this.name;
    }

    public int getId() {
        return this.id;
    }

    public int getRequiredVersion() {
        return this.requiredVersion;
    }

    public boolean hasProperty(ParticleProperty property) {
        return this.properties.contains((Object)property);
    }

    public boolean isSupported() {
        if (this.requiredVersion == -1) {
            return true;
        }
        if (ParticlePacket.getVersion() >= this.requiredVersion) {
            return true;
        }
        return false;
    }

    public static ParticleEffects fromName(String name) {
        for (Map.Entry<String, ParticleEffects> entry : NAME_MAP.entrySet()) {
            if (!entry.getKey().equalsIgnoreCase(name)) continue;
            return entry.getValue();
        }
        return null;
    }

    public static ParticleEffects fromId(int id) {
        for (Map.Entry<Integer, ParticleEffects> entry : ID_MAP.entrySet()) {
            if (entry.getKey() != id) continue;
            return entry.getValue();
        }
        return null;
    }

    private static boolean isWater(Location location) {
        Material material2 = location.getBlock().getType();
        if (material2 != Material.WATER && material2 != Material.STATIONARY_WATER) {
            return false;
        }
        return true;
    }

    private static boolean isLongDistance(Location location, List<Player> players) {
        for (Player player : players) {
            if (player.getLocation().distanceSquared(location) < 65536.0) continue;
            return true;
        }
        return false;
    }

    private static boolean isDataCorrect(ParticleEffects effect, ParticleData data) {
        if (!((effect == BLOCK_CRACK || effect == BLOCK_DUST) && data instanceof BlockData || effect == ITEM_CRACK && data instanceof ItemData)) {
            return false;
        }
        return true;
    }

    private static boolean isColorCorrect(ParticleEffects effect, ParticleColor color) {
        if (!((effect == SPELL_MOB || effect == SPELL_MOB_AMBIENT || effect == REDSTONE) && color instanceof OrdinaryColor || effect == NOTE && color instanceof NoteColor)) {
            return false;
        }
        return true;
    }

    public void display(float offsetX, float offsetY, float offsetZ, float speed, int amount, Location center, double range) throws ParticleVersionException, ParticleDataException, IllegalArgumentException {
        if (!this.isSupported()) {
            throw new ParticleVersionException("This particle effect is not supported by your server version");
        }
        if (this.hasProperty(ParticleProperty.REQUIRES_DATA)) {
            throw new ParticleDataException("This particle effect requires additional data");
        }
        if (this.hasProperty(ParticleProperty.REQUIRES_WATER) && !ParticleEffects.isWater(center)) {
            throw new IllegalArgumentException("There is no water at the center location");
        }
        new ParticlePacket(this, offsetX, offsetY, offsetZ, speed, amount, range > 256.0, null).sendTo(center, range);
    }

    public void display(float offsetX, float offsetY, float offsetZ, float speed, int amount, Location center, List<Player> players) throws ParticleVersionException, ParticleDataException, IllegalArgumentException {
        if (!this.isSupported()) {
            throw new ParticleVersionException("This particle effect is not supported by your server version");
        }
        if (this.hasProperty(ParticleProperty.REQUIRES_DATA)) {
            throw new ParticleDataException("This particle effect requires additional data");
        }
        if (this.hasProperty(ParticleProperty.REQUIRES_WATER) && !ParticleEffects.isWater(center)) {
            throw new IllegalArgumentException("There is no water at the center location");
        }
        new ParticlePacket(this, offsetX, offsetY, offsetZ, speed, amount, ParticleEffects.isLongDistance(center, players), null).sendTo(center, players);
    }

    public /* varargs */ void display(float offsetX, float offsetY, float offsetZ, float speed, int amount, Location center, Player ... players) throws ParticleVersionException, ParticleDataException, IllegalArgumentException {
        this.display(offsetX, offsetY, offsetZ, speed, amount, center, Arrays.asList(players));
    }

    public void display(Vector direction, float speed, Location center, double range) throws ParticleVersionException, ParticleDataException, IllegalArgumentException {
        if (!this.isSupported()) {
            throw new ParticleVersionException("This particle effect is not supported by your server version");
        }
        if (this.hasProperty(ParticleProperty.REQUIRES_DATA)) {
            throw new ParticleDataException("This particle effect requires additional data");
        }
        if (!this.hasProperty(ParticleProperty.DIRECTIONAL)) {
            throw new IllegalArgumentException("This particle effect is not directional");
        }
        if (this.hasProperty(ParticleProperty.REQUIRES_WATER) && !ParticleEffects.isWater(center)) {
            throw new IllegalArgumentException("There is no water at the center location");
        }
        new ParticlePacket(this, direction, speed, range > 256.0, null).sendTo(center, range);
    }

    public void display(Vector direction, float speed, Location center, List<Player> players) throws ParticleVersionException, ParticleDataException, IllegalArgumentException {
        if (!this.isSupported()) {
            throw new ParticleVersionException("This particle effect is not supported by your server version");
        }
        if (this.hasProperty(ParticleProperty.REQUIRES_DATA)) {
            throw new ParticleDataException("This particle effect requires additional data");
        }
        if (!this.hasProperty(ParticleProperty.DIRECTIONAL)) {
            throw new IllegalArgumentException("This particle effect is not directional");
        }
        if (this.hasProperty(ParticleProperty.REQUIRES_WATER) && !ParticleEffects.isWater(center)) {
            throw new IllegalArgumentException("There is no water at the center location");
        }
        new ParticlePacket(this, direction, speed, ParticleEffects.isLongDistance(center, players), null).sendTo(center, players);
    }

    public /* varargs */ void display(Vector direction, float speed, Location center, Player ... players) throws ParticleVersionException, ParticleDataException, IllegalArgumentException {
        this.display(direction, speed, center, Arrays.asList(players));
    }

    public void display(ParticleColor color, Location center, double range) throws ParticleVersionException, ParticleColorException {
        if (!this.isSupported()) {
            throw new ParticleVersionException("This particle effect is not supported by your server version");
        }
        if (!this.hasProperty(ParticleProperty.COLORABLE)) {
            throw new ParticleColorException("This particle effect is not colorable");
        }
        if (!ParticleEffects.isColorCorrect(this, color)) {
            throw new ParticleColorException("The particle color type is incorrect");
        }
        new ParticlePacket(this, color, range > 256.0).sendTo(center, range);
    }

    public void display(ParticleColor color, Location center, List<Player> players) throws ParticleVersionException, ParticleColorException {
        if (!this.isSupported()) {
            throw new ParticleVersionException("This particle effect is not supported by your server version");
        }
        if (!this.hasProperty(ParticleProperty.COLORABLE)) {
            throw new ParticleColorException("This particle effect is not colorable");
        }
        if (!ParticleEffects.isColorCorrect(this, color)) {
            throw new ParticleColorException("The particle color type is incorrect");
        }
        new ParticlePacket(this, color, ParticleEffects.isLongDistance(center, players)).sendTo(center, players);
    }

    public /* varargs */ void display(ParticleColor color, Location center, Player ... players) throws ParticleVersionException, ParticleColorException {
        this.display(color, center, Arrays.asList(players));
    }

    public void display(ParticleData data, float offsetX, float offsetY, float offsetZ, float speed, int amount, Location center, double range) throws ParticleVersionException, ParticleDataException {
        if (!this.isSupported()) {
            throw new ParticleVersionException("This particle effect is not supported by your server version");
        }
        if (!this.hasProperty(ParticleProperty.REQUIRES_DATA)) {
            throw new ParticleDataException("This particle effect does not require additional data");
        }
        if (!ParticleEffects.isDataCorrect(this, data)) {
            throw new ParticleDataException("The particle data type is incorrect");
        }
        new ParticlePacket(this, offsetX, offsetY, offsetZ, speed, amount, range > 256.0, data).sendTo(center, range);
    }

    public void display(ParticleData data, float offsetX, float offsetY, float offsetZ, float speed, int amount, Location center, List<Player> players) throws ParticleVersionException, ParticleDataException {
        if (!this.isSupported()) {
            throw new ParticleVersionException("This particle effect is not supported by your server version");
        }
        if (!this.hasProperty(ParticleProperty.REQUIRES_DATA)) {
            throw new ParticleDataException("This particle effect does not require additional data");
        }
        if (!ParticleEffects.isDataCorrect(this, data)) {
            throw new ParticleDataException("The particle data type is incorrect");
        }
        new ParticlePacket(this, offsetX, offsetY, offsetZ, speed, amount, ParticleEffects.isLongDistance(center, players), data).sendTo(center, players);
    }

    public /* varargs */ void display(ParticleData data, float offsetX, float offsetY, float offsetZ, float speed, int amount, Location center, Player ... players) throws ParticleVersionException, ParticleDataException {
        this.display(data, offsetX, offsetY, offsetZ, speed, amount, center, Arrays.asList(players));
    }

    public void display(ParticleData data, Vector direction, float speed, Location center, double range) throws ParticleVersionException, ParticleDataException {
        if (!this.isSupported()) {
            throw new ParticleVersionException("This particle effect is not supported by your server version");
        }
        if (!this.hasProperty(ParticleProperty.REQUIRES_DATA)) {
            throw new ParticleDataException("This particle effect does not require additional data");
        }
        if (!ParticleEffects.isDataCorrect(this, data)) {
            throw new ParticleDataException("The particle data type is incorrect");
        }
        new ParticlePacket(this, direction, speed, range > 256.0, data).sendTo(center, range);
    }

    public void display(ParticleData data, Vector direction, float speed, Location center, List<Player> players) throws ParticleVersionException, ParticleDataException {
        if (!this.isSupported()) {
            throw new ParticleVersionException("This particle effect is not supported by your server version");
        }
        if (!this.hasProperty(ParticleProperty.REQUIRES_DATA)) {
            throw new ParticleDataException("This particle effect does not require additional data");
        }
        if (!ParticleEffects.isDataCorrect(this, data)) {
            throw new ParticleDataException("The particle data type is incorrect");
        }
        new ParticlePacket(this, direction, speed, ParticleEffects.isLongDistance(center, players), data).sendTo(center, players);
    }

    public /* varargs */ void display(ParticleData data, Vector direction, float speed, Location center, Player ... players) throws ParticleVersionException, ParticleDataException {
        this.display(data, direction, speed, center, Arrays.asList(players));
    }

    public static final class BlockData
    extends ParticleData {
        public BlockData(Material material2, byte data) throws IllegalArgumentException {
            super(material2, data);
            if (!material2.isBlock()) {
                throw new IllegalArgumentException("The material is not a block");
            }
        }
    }

    public static final class ItemData
    extends ParticleData {
        public ItemData(Material material2, byte data) {
            super(material2, data);
        }
    }

    public static final class NoteColor
    extends ParticleColor {
        private final int note;

        public NoteColor(int note) throws IllegalArgumentException {
            if (note < 0) {
                throw new IllegalArgumentException("The note value is lower than 0");
            }
            if (note > 24) {
                throw new IllegalArgumentException("The note value is higher than 24");
            }
            this.note = note;
        }

        @Override
        public float getValueX() {
            return (float)this.note / 24.0f;
        }

        @Override
        public float getValueY() {
            return 0.0f;
        }

        @Override
        public float getValueZ() {
            return 0.0f;
        }
    }

    public static final class OrdinaryColor
    extends ParticleColor {
        private final int red;
        private final int green;
        private final int blue;

        public OrdinaryColor(int red, int green, int blue) throws IllegalArgumentException {
            if (red < 0) {
                throw new IllegalArgumentException("The red value is lower than 0");
            }
            if (red > 255) {
                throw new IllegalArgumentException("The red value is higher than 255");
            }
            this.red = red;
            if (green < 0) {
                throw new IllegalArgumentException("The green value is lower than 0");
            }
            if (green > 255) {
                throw new IllegalArgumentException("The green value is higher than 255");
            }
            this.green = green;
            if (blue < 0) {
                throw new IllegalArgumentException("The blue value is lower than 0");
            }
            if (blue > 255) {
                throw new IllegalArgumentException("The blue value is higher than 255");
            }
            this.blue = blue;
        }

        public int getRed() {
            return this.red;
        }

        public int getGreen() {
            return this.green;
        }

        public int getBlue() {
            return this.blue;
        }

        @Override
        public float getValueX() {
            return (float)this.red / 255.0f;
        }

        @Override
        public float getValueY() {
            return (float)this.green / 255.0f;
        }

        @Override
        public float getValueZ() {
            return (float)this.blue / 255.0f;
        }
    }

    public static abstract class ParticleColor {
        public abstract float getValueX();

        public abstract float getValueY();

        public abstract float getValueZ();
    }

    private static final class ParticleColorException
    extends RuntimeException {
        private static final long serialVersionUID = 3203085387160737484L;

        public ParticleColorException(String message2) {
            super(message2);
        }
    }

    public static abstract class ParticleData {
        private final Material material;
        private final byte data;
        private final int[] packetData;

        public ParticleData(Material material2, byte data) {
            this.material = material2;
            this.data = data;
            this.packetData = new int[]{material2.getId(), data};
        }

        public Material getMaterial() {
            return this.material;
        }

        public byte getData() {
            return this.data;
        }

        public int[] getPacketData() {
            return this.packetData;
        }

        public String getPacketDataString() {
            return "_" + this.packetData[0] + "_" + this.packetData[1];
        }
    }

    private static final class ParticleDataException
    extends RuntimeException {
        private static final long serialVersionUID = 3203085387160737484L;

        public ParticleDataException(String message2) {
            super(message2);
        }
    }

    public static final class ParticlePacket {
        private static int version = 7;
        private static Class<?> enumParticle;
        private static Constructor<?> packetConstructor;
        private static Method getHandle;
        private static Field playerConnection;
        private static Method sendPacket;
        private static boolean initialized;
        private final ParticleEffects effect;
        private final float offsetX;
        private final float offsetY;
        private final float offsetZ;
        private final float speed;
        private final int amount;
        private final boolean longDistance;
        private final ParticleData data;
        private Object packet;

        public ParticlePacket(ParticleEffects effect, float offsetX, float offsetY, float offsetZ, float speed, int amount, boolean longDistance, ParticleData data) throws IllegalArgumentException {
            ParticlePacket.initialize();
            if (speed < 0.0f) {
                throw new IllegalArgumentException("The speed is lower than 0");
            }
            if (amount < 0) {
                throw new IllegalArgumentException("The amount is lower than 0");
            }
            this.effect = effect;
            this.offsetX = offsetX;
            this.offsetY = offsetY;
            this.offsetZ = offsetZ;
            this.speed = speed;
            this.amount = amount;
            this.longDistance = longDistance;
            this.data = data;
        }

        public ParticlePacket(ParticleEffects effect, Vector direction, float speed, boolean longDistance, ParticleData data) throws IllegalArgumentException {
            this(effect, (float)direction.getX(), (float)direction.getY(), (float)direction.getZ(), speed, 0, longDistance, data);
        }

        public ParticlePacket(ParticleEffects effect, ParticleColor color, boolean longDistance) {
            this(effect, color.getValueX(), color.getValueY(), color.getValueZ(), 1.0f, 0, longDistance, null);
        }

        public static void initialize() throws VersionIncompatibleException {
            if (initialized) {
                return;
            }
            try {
                version = Integer.parseInt(ReflectionUtils.PackageType.getServerVersion().split("_")[1]);
            }
            catch (Exception exception) {
                throw new VersionIncompatibleException("Your current bukkit version seems to be incompatible with this library", exception);
            }
            try {
                if (version > 7) {
                    enumParticle = ReflectionUtils.PackageType.MINECRAFT_SERVER.getClass("EnumParticle");
                }
                Class packetClass = ReflectionUtils.PackageType.MINECRAFT_SERVER.getClass(version < 7 ? "Packet63WorldParticles" : "PacketPlayOutWorldParticles");
                packetConstructor = ReflectionUtils.getConstructor(packetClass, new Class[0]);
                getHandle = ReflectionUtils.getMethod("CraftPlayer", ReflectionUtils.PackageType.CRAFTBUKKIT_ENTITY, "getHandle", new Class[0]);
                playerConnection = ReflectionUtils.getField("EntityPlayer", ReflectionUtils.PackageType.MINECRAFT_SERVER, false, "playerConnection");
                sendPacket = ReflectionUtils.getMethod(playerConnection.getType(), "sendPacket", ReflectionUtils.PackageType.MINECRAFT_SERVER.getClass("Packet"));
            }
            catch (Exception exception) {
                throw new VersionIncompatibleException("Your current bukkit version seems to be incompatible with this library", exception);
            }
            initialized = true;
        }

        public static int getVersion() {
            return version;
        }

        public static boolean isInitialized() {
            return initialized;
        }

        private void initializePacket(Location center) throws PacketInstantiationException {
            if (this.packet != null) {
                return;
            }
            try {
                this.packet = packetConstructor.newInstance(new Object[0]);
                if (version < 8) {
                    String name = this.effect.getName();
                    if (this.data != null) {
                        name = String.valueOf(name) + this.data.getPacketDataString();
                    }
                    ReflectionUtils.setValue(this.packet, true, "a", name);
                } else {
                    ReflectionUtils.setValue(this.packet, true, "a", enumParticle.getEnumConstants()[this.effect.getId()]);
                    ReflectionUtils.setValue(this.packet, true, "j", this.longDistance);
                    if (this.data != null) {
                        ReflectionUtils.setValue(this.packet, true, "k", this.data.getPacketData());
                    }
                }
                ReflectionUtils.setValue(this.packet, true, "b", Float.valueOf((float)center.getX()));
                ReflectionUtils.setValue(this.packet, true, "c", Float.valueOf((float)center.getY()));
                ReflectionUtils.setValue(this.packet, true, "d", Float.valueOf((float)center.getZ()));
                ReflectionUtils.setValue(this.packet, true, "e", Float.valueOf(this.offsetX));
                ReflectionUtils.setValue(this.packet, true, "f", Float.valueOf(this.offsetY));
                ReflectionUtils.setValue(this.packet, true, "g", Float.valueOf(this.offsetZ));
                ReflectionUtils.setValue(this.packet, true, "h", Float.valueOf(this.speed));
                ReflectionUtils.setValue(this.packet, true, "i", this.amount);
            }
            catch (Exception exception) {
                throw new PacketInstantiationException("Packet instantiation failed", exception);
            }
        }

        public void sendTo(Location center, Player player) throws PacketInstantiationException, PacketSendingException {
            this.initializePacket(center);
            try {
                sendPacket.invoke(playerConnection.get(getHandle.invoke((Object)player, new Object[0])), this.packet);
            }
            catch (Exception exception) {
                throw new PacketSendingException("Failed to send the packet to player '" + player.getName() + "'", exception);
            }
        }

        public void sendTo(Location center, List<Player> players) throws IllegalArgumentException {
            if (players.isEmpty()) {
                throw new IllegalArgumentException("The player list is empty");
            }
            for (Player player : players) {
                this.sendTo(center, player);
            }
        }

        public void sendTo(Location center, double range) throws IllegalArgumentException {
            if (range < 1.0) {
                throw new IllegalArgumentException("The range is lower than 1");
            }
            String worldName = center.getWorld().getName();
            double squared = range * range;
            for (Player player : Bukkit.getOnlinePlayers()) {
                if (!player.getWorld().getName().equals(worldName) || player.getLocation().distanceSquared(center) > squared) continue;
                this.sendTo(center, player);
            }
        }

        private static final class PacketInstantiationException
        extends RuntimeException {
            private static final long serialVersionUID = 3203085387160737484L;

            public PacketInstantiationException(String message2, Throwable cause) {
                super(message2, cause);
            }
        }

        private static final class PacketSendingException
        extends RuntimeException {
            private static final long serialVersionUID = 3203085387160737484L;

            public PacketSendingException(String message2, Throwable cause) {
                super(message2, cause);
            }
        }

        private static final class VersionIncompatibleException
        extends RuntimeException {
            private static final long serialVersionUID = 3203085387160737484L;

            public VersionIncompatibleException(String message2, Throwable cause) {
                super(message2, cause);
            }
        }

    }

    public static enum ParticleProperty {
        REQUIRES_WATER,
        REQUIRES_DATA,
        DIRECTIONAL,
        COLORABLE;
        

        private ParticleProperty(String string2, int n2) {
        }
    }

    private static final class ParticleVersionException
    extends RuntimeException {
        private static final long serialVersionUID = 3203085387160737484L;

        public ParticleVersionException(String message2) {
            super(message2);
        }
    }

}

