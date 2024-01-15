import {useEffect, useState} from "react";
import axios from "axios";
import {TrashType} from "../types/TrashType.ts";
import styled from "styled-components";

export default function Trash() {

    const [trashes, setTrashes] = useState<TrashType[]>([])

    useEffect(() => {
            getTrashes()
        }, []
    )

    function getTrashes() {
        axios.get("api/trash")
            .then(response => setTrashes((response.data)))
    }

    return (
        <StyledSection>
            {trashes.map((trash, index) => (
                <StyledTrash key={trash.id} index={index + 1}>
                    {`${trash.name}`}</StyledTrash>
            ))}
        </StyledSection>
    )
}

const StyledSection = styled.section`
  display: grid;
  grid-template-columns: repeat(8, 1fr);
  gap: 8px;
  grid-template-areas:
    "area1 area1 area2 area2 area3 area3 area4 area4"
    "area1 area1 area2 area2 area3 area3 area4 area4"
    "area1 area1 area2 area2 area7 area7 area4 area4"
    "area5 area5 area2 area2 area7 area7 area9 area9"
    "area5 area5 area6 area6 area7 area7 area9 area9"
    "area5 area5 area6 area6 area8 area8 area9 area9"
    "area5 area5 area6 area6 area8 area8 area9 area9"
    "area11 area11 area11 area10 area10 area12 area12 area12";
`;

const StyledTrash = styled.div<{ index: number }>`
  grid-area: ${(props) => `area${props.index}`};
  display: flex;
  align-items: center;
  justify-content: center;
  border: 1px solid white;
  padding: 8px;
  height: 100%;
  box-sizing: border-box;
`;