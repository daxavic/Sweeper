package sweeper;

class Bomb
{
    private Matrix bombMap;
    private int totalBombs;

    Bomb (int totalBombs)
    {
        this.totalBombs = totalBombs;
        fixBombCount();
    }

    void start ()
    {
        bombMap = new Matrix(Box.ZERO);
        for (int i = 0; i < totalBombs; i++)
        placeBomb();
    }

    Box get(Coord coord)
    {
        return bombMap.get(coord);
    }

    private void fixBombCount()
    {
        int maxBombs = Ranges.getSize().x * Ranges.getSize().y / 2;
        if (totalBombs > maxBombs )
            totalBombs = maxBombs;
    }

    private void placeBomb()
    {
        while (true)
        {
            Coord coord = Ranges.getRandomCoord();
            if (Box.BOMB == bombMap.get(coord))
                continue;
            bombMap.set(new Coord(coord.x,coord.y), Box.BOMB);
            incNumbersaroundBomb(coord);
            break;
        }

    }

    private void incNumbersaroundBomb (Coord coord)
    {
        for (Coord around: Ranges.getCoordAround(coord))
            if (Box.BOMB != bombMap.get(around))
                bombMap.set(around, bombMap.get(around).getNextNumberBox());
    }


    int getTotalBombs()
    {
        return totalBombs;
    }
}
