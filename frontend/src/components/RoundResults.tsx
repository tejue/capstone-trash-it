import {useState} from "react";
import axios from "axios";
import {RoundType} from "../types/RoundType.ts";
import styled from "styled-components";

export default function RoundResults() {

    const [rounds, setRounds] = useState<RoundType[]>([])

    const playerId: string = "1"

    function getRoundsResults() {
        axios.get(`/api/game/${playerId}/rounds`)
            .then(response => {
                setRounds(response.data);
            })
    }

    return (
        <>
            <button onClick={getRoundsResults}>Click</button>
            {rounds
                .map((round, index) => (
                    <StyledSection key={index}>
                        <p>{`Round: ${round.roundNumber}`}</p>
                        <p>{`You trashed ${round.trashedTotal} / ${round.garbageTotal}`}</p>
                    </StyledSection>
                ))}
        </>
    );
}

const StyledSection = styled.section`
  display: flex;
  gap: 20px;
`