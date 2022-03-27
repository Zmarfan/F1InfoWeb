package f1_Info.constants;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;

@Getter
@AllArgsConstructor
public enum FinishStatus {
    FINISHED(1, "finished"),
    DISQUALIFIED(2, "disqualified"),
    ACCIDENT(3, "accident"),
    COLLISION(4, "collision"),
    ENGINE(5, "engine"),
    GEARBOX(6, "gearbox"),
    TRANSMISSION(7, "transmission"),
    CLUTCH(8, "clutch"),
    HYDRAULICS(9, "hydraulics"),
    ELECTRICAL(10, "electrical"),
    PLUS_1_LAP(11, "+1 lap"),
    PLUS_2_LAPS(12, "+2 laps"),
    PLUS_3_LAPS(13, "+3 laps"),
    PLUS_4_LAPS(14, "+4 laps"),
    PLUS_5_LAPS(15, "+5 laps"),
    PLUS_6_LAPS(16, "+6 laps"),
    PLUS_7_LAPS(17, "+7 laps"),
    PLUS_8_LAPS(18, "+8 laps"),
    PLUS_9_LAPS(19, "+9 laps"),
    SPUN_OFF(20, "spun off"),
    RADIATOR(21, "radiator"),
    SUSPENSION(22, "suspension"),
    BRAKES(23, "brakes"),
    DIFFERENTIAL(24, "differential"),
    OVERHEATING(25, "overheating"),
    MECHANICAL(26, "mechanical"),
    TYRE(27, "tyre"),
    DRIVER_SEAT(28, "driver seat"),
    PUNCTURE(29, "puncture"),
    DRIVESHAFT(30, "driveshaft"),
    RETIRED(31, "retired"),
    FUEL_PRESSURE(32, "fuel pressure"),
    FRONT_WING(33, "front wing"),
    WATER_PRESSURE(34, "water pressure"),
    REFUELLING(35, "refuelling"),
    WHEEL(36, "wheel"),
    THROTTLE(37, "throttle"),
    STEERING(38, "steering"),
    TECHNICAL(39, "technical"),
    ELECTRONICS(40, "electronics"),
    BROKEN_WING(41, "broken wing"),
    HEAT_SHIELD_FIRE(42, "heat shield fire"),
    EXHAUST(43, "exhaust"),
    OIL_LEAK(44, "oil leak"),
    PLUS_11_LAPS(45, "+11 laps"),
    WHEEL_RIM(46, "wheel rim"),
    WATER_LEAK(47, "water leak"),
    FUEL_PUMP(48, "fuel pump"),
    TRACK_ROD(49, "track rod"),
    PLUS_17_LAPS(50, "+17 laps"),
    OIL_PRESSURE(51, "oil pressure"),
    PLUS_13_LAPS(53, "+13 laps"),
    WITHDREW(54, "withdrew"),
    PLUS_12_LAPS(55, "+12 laps"),
    ENGINE_FIRE(56, "engine fire"),
    PLUS_26_LAPS(58, "+26 laps"),
    TYRE_PUNCTURE(59, "tyre puncture"),
    OUT_OF_FUEL(60, "out of fuel"),
    WHEEL_NUT(61, "wheel nut"),
    NOT_CLASSIFIED(62, "not classified"),
    PNEUMATICS(63, "pneumatics"),
    HANDLING(64, "handling"),
    REAR_WING(65, "rear wing"),
    FIRE(66, "fire"),
    WHEEL_BEARING(67, "wheel bearing"),
    PHYSICAL(68, "physical"),
    FUEL_SYSTEM(69, "fuel system"),
    OIL_LINE(70, "oil line"),
    FUEL_RIG(71, "fuel rig"),
    LAUNCH_CONTROL(72, "launch control"),
    INJURED(73, "injured"),
    FUEL(74, "fuel"),
    POWER_LOSS(75, "power loss"),
    VIBRATIONS(76, "vibrations"),
    PERCENTAGE_107_RULE(77, "107% rule"),
    SAFETY(78, "safety"),
    DRIVETRAIN(79, "drivetrain"),
    IGNITION(80, "ignition"),
    DID_NOT_QUALIFY(81, "did not qualify"),
    INJURY(82, "injury"),
    CHASSIS(83, "chassis"),
    BATTERY(84, "battery"),
    STALLED(85, "stalled"),
    HALFSHAFT(86, "halfshaft"),
    CRANKSHAFT(87, "crankshaft"),
    PLUS_10_LAPS(88, "+10 laps"),
    SAFETY_CONCERNS(89, "safety concerns"),
    NOT_RESTARTED(90, "not restarted"),
    ALTERNATOR(91, "alternator"),
    UNDERWEIGHT(92, "underweight"),
    SAFETY_BELT(93, "safety belt"),
    OIL_PUMP(94, "oil pump"),
    FUEL_LEAK(95, "fuel leak"),
    EXCLUDED(96, "excluded"),
    DID_NOT_PREQUALIFY(97, "did not prequalify"),
    INJECTION(98, "injection"),
    DISTRIBUTOR(99, "distributor"),
    DRIVER_UNWELL(100, "driver unwell"),
    TURBO(101, "turbo"),
    CV_JOINT(102, "cv joint"),
    WATER_PUMP(103, "water pump"),
    FATAL_ACCIDENT(104, "fatal accident"),
    SPARK_PLUGS(105, "spark plugs"),
    FUEL_PIPE(106, "fuel pipe"),
    EYE_INJURY(107, "eye injury"),
    OIL_PIPE(108, "oil pipe"),
    AXLE(109, "axle"),
    WATER_PIPE(110, "water pipe"),
    PLUS_14_LAPS(111, "+14 laps"),
    PLUS_15_LAPS(112, "+15 laps"),
    PLUS_25_LAPS(113, "+25 laps"),
    PLUS_18_LAPS(114, "+18 laps"),
    PLUS_22_LAPS(115, "+22 laps"),
    PLUS_16_LAPS(116, "+16 laps"),
    PLUS_24_LAPS(117, "+24 laps"),
    PLUS_29_LAPS(118, "+29 laps"),
    PLUS_23_LAPS(119, "+23 laps"),
    PLUS_21_LAPS(120, "+21 laps"),
    MAGNETO(121, "magneto"),
    PLUS_44_LAPS(122, "+44 laps"),
    PLUS_30_LAPS(123, "+30 laps"),
    PLUS_19_LAPS(124, "+19 laps"),
    PLUS_46_LAPS(125, "+46 laps"),
    SUPERCHARGER(126, "supercharger"),
    PLUS_20_LAPS(127, "+20 laps"),
    PLUS_42_LAPS(128, "+42 laps"),
    ENGINE_MISFIRE(129, "engine misfire"),
    COLLISION_DAMAGE(130, "collision damage"),
    POWER_UNIT(131, "power unit"),
    ERS(132, "ers"),
    BRAKE_DUCT(135, "brake duct"),
    SEAT(136, "seat"),
    DAMAGE(137, "damage"),
    DEBRIS(138, "debris"),
    ILLNESS(139, "illness");

    private final int id;
    private final String name;

    public static FinishStatus fromId(final int id) {
        return Arrays.stream(values())
            .filter(finishStatus -> finishStatus.id == id)
            .findFirst()
            .orElseThrow(() -> new IllegalArgumentException(String.format("Unable to parse the id: %d to a valid finish status", id)));
    }

    public static FinishStatus fromName(final String name) {
        return Arrays.stream(values())
            .filter(finishStatus -> finishStatus.name.equals(name))
            .findFirst()
            .orElseThrow(() -> new IllegalArgumentException(String.format("Unable to parse the name: %s to a valid finish status", name)));
    }
}