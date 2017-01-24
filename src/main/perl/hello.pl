#!/usr/bin/perl

my $today = "1396633473";
my $age = "1396633569";

print $age - $today . "\n";

print "Component Name";
print " " x 42;
print "Import";
print " " x 4;
print "Import Max";
print " " x 4;
print "Export";
print " " x 4;
print "Export Max";
print " " x 4;
print "Last Processed";
print " " x 4;
print "Inactive";
print " " x 4;
print "Instances";
print " " x 4;
print "Processes";
print " " x 4;
print "Queue Type";
print " " x 4;
print "Config";
print " " x 4;
print "Fatals";
print "\n";
print " " x 67;
print "Age (sec)";
print " " x 15;
print "Age (sec)";
print " " x 5;
print "(sec)";
print " " x 13;
print "(sec)";
print "\n";

$component = "publisher-dms-archive-warnings-mfile-4.2-build.449";
$importQueueSize = "34";
$importQueueMaxAge = "345";
$exportQueueSize = "34";
$exportQueueMaxAge = "345";
$timeLastProcessed = "2412";
$timeInactive = "3";
$threadNum = "1";
$processStr = "16 of 16";
$queueTypes = "non-exclusive";
$configState = "true";
$fatals = "0";

print "$component:";
print " " x ( 55 - ( length( $component ) ) );

# output the size of the import queue
print "$importQueueSize";
print " " x ( 10 - length($importQueueSize) );

# output the max age of the import queue
print "$importQueueMaxAge";
print " " x ( 14 - length($importQueueMaxAge) );

# output the size of the export queue
print "$exportQueueSize";
print " " x ( 10 - length($exportQueueSize) );

# output the max age of the export queue
print "$exportQueueMaxAge";
print " " x ( 14 - length($exportQueueMaxAge) );

# output the time since the component processed something
print "$timeLastProcessed";
print " " x ( 18 - length($timeLastProcessed) );

# output the time the component has been inactive for
print "$timeInactive";
print " " x ( 12 - length($timeInactive) );

# output the number of instances
print "$threadNum";
print " " x ( 13 - length($threadNum) );

#output the number of processes
print "$processStr";
print " " x ( 13 - length($processStr) );

print "$queueTypes";
print " " x ( 14 - length( $queueTypes ) );

# output the configuration state for the component
print "$configState";
print " " x ( 10 - length($configState) );

#output the number of fatal errors for the componentConfigFile
print "$fatals\n";