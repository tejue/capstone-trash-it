import {useEffect, useState} from "react";
import axios from "axios";
import {TrashCanType} from "../types/TrashCanType.ts";
import styled from "styled-components";

export default function Game() {

    const [trashCans, setTrashCans] = useState<TrashCanType[]>([])

    useEffect(() => {
            getTrashes()
        }, []
    )
    ;

    function getTrashes() {
        axios.get("api/trashcan")
            .then(response => setTrashCans((response.data)))
    }

    return (

            <StyledSection>
            {trashCans.map((trashCan) => (
                <StyledTrashCan key={trashCan.id}>
                    {`${trashCan.name}`}
                </StyledTrashCan>))}
            </StyledSection>

    )
}

const StyledSection = styled.section`
  display: flex;
  gap: 10px;
  margin: 20px;
  justify-content: center;
`

const StyledTrashCan = styled.div`
  border: solid white 1px;
  text-align: center;
`