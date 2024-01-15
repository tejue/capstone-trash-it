import styled from "styled-components";
import {TrashCanType} from "../types/TrashCanType.ts";
import DroppableTrashCan from "./DroppableTrashCan.tsx";

type TrashCanProps = {
    trashCans: TrashCanType [];
}

export default function TrashCan(props: TrashCanProps) {

    return (
        <StyledSection>
            {props.trashCans.map((trashCan) => (
                <DroppableTrashCan key={trashCan.id} trashCan={trashCan}/>
            ))
            }
        </StyledSection>
    )
}

const StyledSection = styled.section`
  display: flex;
  gap: 10px;
  margin: 20px;
  justify-content: center;
`
