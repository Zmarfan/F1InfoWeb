package f1_Info.constants;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;
import java.util.Objects;

@Getter
@AllArgsConstructor
public enum FinishStatus {
    FINISHED(1, "Finished"),
    DISQUALIFIED(2, "Disqualified"),
    ACCIDENT(3, "Accident"),
    COLLISION(4, "Collision"),
    ENGINE(5, "Engine"),
    GEARBOX(6, "Gearbox"),
    TRANSMISSION(7, "Transmission"),
    CLUTCH(8, "Clutch"),
    HYDRAULICS(9, "Hydraulics"),
    ELECTRICAL(10, "Electrical"),
    PLUS_1_LAP(11, "+1 Lap"),
    PLUS_2_LAPS(12, "+2 Laps"),
    PLUS_3_LAPS(13, "+3 Laps"),
    PLUS_4_LAPS(14, "+4 Laps"),
    PLUS_5_LAPS(15, "+5 Laps"),
    PLUS_6_LAPS(16, "+6 Laps"),
    PLUS_7_LAPS(17, "+7 Laps"),
    PLUS_8_LAPS(18, "+8 Laps"),
    PLUS_9_LAPS(19, "+9 Laps"),
    SPUN_OFF(20, "Spun off"),
    RADIATOR(21, "Radiator"),
    SUSPENSION(22, "Suspension"),
    BRAKES(23, "Brakes"),
    DIFFERENTIAL(24, "Differential"),
    OVERHEATING(25, "Overheating"),
    MECHANICAL(26, "Mechanical"),
    TYRE(27, "Tyre"),
    DRIVER_SEAT(28, "Driver Seat"),
    PUNCTURE(29, "Puncture"),
    DRIVESHAFT(30, "Driveshaft"),
    RETIRED(31, "Retired"),
    FUEL_PRESSURE(32, "Fuel pressure"),
    FRONT_WING(33, "Front wing"),
    WATER_PRESSURE(34, "Water pressure"),
    REFUELLING(35, "Refuelling"),
    WHEEL(36, "Wheel"),
    THROTTLE(37, "Throttle"),
    STEERING(38, "Steering"),
    TECHNICAL(39, "Technical"),
    ELECTRONICS(40, "Electronics"),
    BROKEN_WING(41, "Broken wing"),
    HEAT_SHIELD_FIRE(42, "Heat shield fire"),
    EXHAUST(43, "Exhaust"),
    OIL_LEAK(44, "Oil leak"),
    PLUS_11_LAPS(45, "+11 Laps"),
    WHEEL_RIM(46, "Wheel rim"),
    WATER_LEAK(47, "Water leak"),
    FUEL_PUMP(48, "Fuel pump"),
    TRACK_ROD(49, "Track rod"),
    PLUS_17_LAPS(50, "+17 Laps"),
    OIL_PRESSURE(51, "Oil pressure"),
    PLUS_13_LAPS(53, "+13 Laps"),
    WITHDREW(54, "Withdrew"),
    PLUS_12_LAPS(55, "+12 Laps"),
    ENGINE_FIRE(56, "Engine fire"),
    PLUS_26_LAPS(58, "+26 Laps"),
    TYRE_PUNCTURE(59, "Tyre puncture"),
    OUT_OF_FUEL(60, "Out of fuel"),
    WHEEL_NUT(61, "Wheel nut"),
    NOT_CLASSIFIED(62, "Not classified"),
    PNEUMATICS(63, "Pneumatics"),
    HANDLING(64, "Handling"),
    REAR_WING(65, "Rear wing"),
    FIRE(66, "Fire"),
    WHEEL_BEARING(67, "Wheel bearing"),
    PHYSICAL(68, "Physical"),
    FUEL_SYSTEM(69, "Fuel system"),
    OIL_LINE(70, "Oil line"),
    FUEL_RIG(71, "Fuel rig"),
    LAUNCH_CONTROL(72, "Launch control"),
    INJURED(73, "Injured"),
    FUEL(74, "Fuel"),
    POWER_LOSS(75, "Power loss"),
    VIBRATIONS(76, "Vibrations"),
    PERCENTAGE_107_RULE(77, "107% Rule"),
    SAFETY(78, "Safety"),
    DRIVETRAIN(79, "Drivetrain"),
    IGNITION(80, "Ignition"),
    DID_NOT_QUALIFY(81, "Did not qualify"),
    INJURY(82, "Injury"),
    CHASSIS(83, "Chassis"),
    BATTERY(84, "Battery"),
    STALLED(85, "Stalled"),
    HALFSHAFT(86, "Halfshaft"),
    CRANKSHAFT(87, "Crankshaft"),
    PLUS_10_LAPS(88, "+10 Laps"),
    SAFETY_CONCERNS(89, "Safety concerns"),
    NOT_RESTARTED(90, "Not restarted"),
    ALTERNATOR(91, "Alternator"),
    UNDERWEIGHT(92, "Underweight"),
    SAFETY_BELT(93, "Safety belt"),
    OIL_PUMP(94, "Oil pump"),
    FUEL_LEAK(95, "Fuel leak"),
    EXCLUDED(96, "Excluded"),
    DID_NOT_PREQUALIFY(97, "Did not prequalify"),
    INJECTION(98, "Injection"),
    DISTRIBUTOR(99, "Distributor"),
    DRIVER_UNWELL(100, "Driver unwell"),
    TURBO(101, "Turbo"),
    CV_JOINT(102, "CV joint"),
    WATER_PUMP(103, "Water pump"),
    FATAL_ACCIDENT(104, "Fatal accident"),
    SPARK_PLUGS(105, "Spark plugs"),
    FUEL_PIPE(106, "Fuel pipe"),
    EYE_INJURY(107, "Eye injury"),
    OIL_PIPE(108, "Oil pipe"),
    AXLE(109, "Axle"),
    WATER_PIPE(110, "Water pipe"),
    PLUS_14_LAPS(111, "+14 Laps"),
    PLUS_15_LAPS(112, "+15 Laps"),
    PLUS_25_LAPS(113, "+25 Laps"),
    PLUS_18_LAPS(114, "+18 Laps"),
    PLUS_22_LAPS(115, "+22 Laps"),
    PLUS_16_LAPS(116, "+16 Laps"),
    PLUS_24_LAPS(117, "+24 Laps"),
    PLUS_29_LAPS(118, "+29 Laps"),
    PLUS_23_LAPS(119, "+23 Laps"),
    PLUS_21_LAPS(120, "+21 Laps"),
    MAGNETO(121, "Magneto"),
    PLUS_44_LAPS(122, "+44 Laps"),
    PLUS_30_LAPS(123, "+30 Laps"),
    PLUS_19_LAPS(124, "+19 Laps"),
    PLUS_46_LAPS(125, "+46 Laps"),
    SUPERCHARGER(126, "Supercharger"),
    PLUS_20_LAPS(127, "+20 Laps"),
    PLUS_42_LAPS(128, "+42 Laps"),
    ENGINE_MISFIRE(129, "Engine misfire"),
    COLLISION_DAMAGE(130, "Collision damage"),
    POWER_UNIT(131, "Power Unit"),
    ERS(132, "ERS"),
    BRAKE_DUCT(135, "Brake duct"),
    SEAT(136, "Seat"),
    DAMAGE(137, "Damage"),
    DEBRIS(138, "Debris"),
    ILLNESS(139, "Illness");

    private final int mId;
    private final String mStringCode;

    public static FinishStatus fromId(final int id) {
        return Arrays.stream(values())
            .filter(finishStatus -> finishStatus.mId == id)
            .findFirst()
            .orElseThrow(() -> new IllegalArgumentException(String.format("Unable to parse the id: %d to a valid finish status", id)));
    }

    public static FinishStatus fromStringCode(final String stringCode) {
        return Arrays.stream(values())
            .filter(finishStatus -> Objects.equals(finishStatus.mStringCode, stringCode))
            .findFirst()
            .orElseThrow(() -> new IllegalArgumentException(String.format("Unable to parse the string code: %s to a valid finish status", stringCode)));
    }

    public static boolean exists(final int id) {
        return Arrays.stream(values()).anyMatch(finishStatus -> finishStatus.mId == id);
    }
}
