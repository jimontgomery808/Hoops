<?php
	//Creating a connection
	//define('DB_SERVER', 'hoopsdb.cpsknmvlzyyf.us-east-2.rds.amazonaws.com');
	//define('DB_USERNAME', 'JMontgomery');
	//define('DB_PASSWORD', '81590Jim');
	//define('DB_DATABASE', 'Hoops');



	$con = mysqli_connect('hoopsdb.cpsknmvlzyyf.us-east-2.rds.amazonaws.com', 'JMontgomery', '81590Jim','Hoops', '3306');
	//$con = mysql_connect(DB_SERVER, DB_USERNAME, DB_PASSWORD);


    if (mysqli_connect_errno())
    {
       echo "Failed to connect to MySQL: " . mysqli_connect_error();
    }
	/*Get the id of the last visible item in the RecyclerView from the request and store it in a variable. For            the first request id will be zero.*/

	$result = mysqli_query($con,"SELECT * FROM current_games");

	echo "<table border='1'>
	<tr>
	<th>gamdeId</th>
	<th>isGameActivated</th>
	<th>startTime</th>
	<th>startDate</th>
	<th>clock</th>
	<th>quarter</th>
	<th>isHalfTime</th>
	<th>isEndOfQuarter</th>
	<th>vTeamAbrvAbrv</th>
	<th>vWinRecord</th>
	<th>vLossRecord</th>
	<th>vTeamScore</th>
	<th>hTeamAbrv</th>
	<th>hWinRecord</th>
	<th>hLossRecord</th>
	<th>hTeamScore</th>
	<th>vWatchShort</th>
	<th>vWatchLong</th>
	<th>hWatchShort</th>
	<th>hWatchLong</th>
	</tr>";

	while($row = mysqli_fetch_array($result))
	{
		echo "<tr>";
		echo "<td>" . $row['gameId'] . "</td>";
		echo "<td>" . $row['isGameActivated'] . "</td>";
		echo "<td>" . $row['startTime'] . "</td>";
		echo "<td>" . $row['startDate'] . "</td>";
		echo "<td>" . $row['clock'] . "</td>";
		echo "<td>" . $row['quarter'] . "</td>";
		echo "<td>" . $row['isHalfTime'] . "</td>";
		echo "<td>" . $row['isEndOfQuarter'] . "</td>";
		echo "<td>" . $row['vTeamAbrv'] . "</td>";
		echo "<td>" . $row['vWinRecord'] . "</td>";
		echo "<td>" . $row['vLossRecord'] . "</td>";
		echo "<td>" . $row['vTeamScore'] . "</td>";
		echo "<td>" . $row['hTeamAbrv'] . "</td>";
		echo "<td>" . $row['hWinRecord'] . "</td>";
		echo "<td>" . $row['hLossRecord'] . "</td>";
		echo "<td>" . $row['hTeamScore'] . "</td>";
		echo "<td>" . $row['vTeamWatchShort'] . "</td>";
		echo "<td>" . $row['vTeamWatchLong'] . "</td>";
		echo "<td>" . $row['hTeamWatchShort'] . "</td>";
		echo "<td>" . $row['hTeamWatchLong'] . "</td>";

		echo "</tr>";
	}
	echo "</table>";

	mysqli_close($con);

 ?>