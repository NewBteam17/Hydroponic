<?php
        $suhu = 30;
        $kekeruhan = 115;

        if ($suhu < 90){
            $kondisi[0] = 1;
        } else if ($suhu > 90 && $suhu < 110){
             $kondisi[0] = (110 - $suhu)/(110 - 90);
        } else {
            $kondisi[0] = 0;
        }  
        
        // normal
        if ($suhu < 90){
             $kondisi[1] = 0;
        } else if ($suhu > 90 && $suhu < 110){
             $kondisi[1] = ($suhu - 90)/(110 - 90);
        } else if ($suhu > 110 && $suhu < 125){
             $kondisi[1] = (125 - $suhu)/(125 - 110);
        } else {
             $kondisi[1] = 0;
        } 
        // tinggi
        if ($suhu < 110){
            $kondisi [2] = 0;
        } else if ($suhu > 110 && $suhu < 125){
             $kondisi[2] = ($suhu-110)/(125 - 110);
        } else {
            $kondisi [2] = 1;
        }

        //kekeruhan rendah
        if ($kekeruhan < 90){
            $sikon[0] = 1;
        } else if ($kekeruhan > 90 && $kekeruhan < 110){
             $kekeruhan[0] = (110 - $kekeruhan)/(110 - 90);
        } else {
            $sikon[0] = 0;
        }  
        
        // normal
        if ($kekeruhan < 110){
             $sikon[1] = 0;
        } else if ($kekeruhan > 90 && $kekeruhan < 110){
             $sikon[1] = ($kekeruhan-90)/(110 - 90);
        } else if ($kekeruhan > 110 && $kekeruhan < 125){
             $sikon[1] = (125-$kekeruhan)/(125 - 110);
        } else {
             $sikon[1] = 0;
        } 
        // tinggi
        if ($kekeruhan < 110){
            $sikon[2] = 0;
        } else if ($kekeruhan > 110 && $kekeruhan < 125){
             $sikon[2] = ($kekeruhan - 110)/(125 - 110);
        } else {
            $sikon[2] = 1;
        }

       $i; $j;

for ($i=0; $i<2; $i=$i+1)
{
     for($j=0; $j<2; $j=$j+1)
     {
         $kualair = min($kondisi[$i], $sikon[$j]);
        $rule [$i][$j] = $kualair;    
     }
} 

    $rules0 = $rule [0][0]; // (jernih, sedang = jernih)
    $rules1 = $rule [0][1]; // (jernih, normal = jernih)
    $rules2 = $rule [0][2]; // (jernih, tinggi = jernih)
    
    $rules3 = $rule [1][0]; // (normal, sedang = jernih)
    $rules4 = $rule [1][1]; // (normal, normal = normal)
    $rules5 = $rule [1][2]; // (normal, tinggi = keruh)

    $rules6 = $rule [2][0]; // (keruh, sedang = keruh)
    $rules7 = $rule [2][1]; // (keruh, normal = keruh)
    $rules8 = $rule [2][2]; // (keruh, tinggi = keruh)

    $jernih = 40;
    $normal = 80;
    $keruh  = 105;

    $ruleskualitas = ($rules0 * $jernih) + ($rules1 * $jernih) + ($rules2 * $jernih) + 
    ($rules3 * $normal) + ($rules4 * $normal) + ($rules5 * $normal) +
    ($rules6 * $keruh) + ($rules7 * $keruh) + ($rules8 * $keruh);

        $defus = 0;
        $i; $j;
         for($i=0; $i<2; $i=$i+1 )
         {
                for($j=0; $j<2; $j=$j+1)
                {
                    $defus = $defus + $rule[$i][$j];
                }
        }
         $result = $ruleskualitas / $defus;
                echo $result;

?>